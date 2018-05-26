
package controllers;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.FolderService;
import services.MessageService;
import domain.Actor;
import domain.Folder;
import domain.Message;
import forms.MessageForm;

@Controller
@RequestMapping("/message/actor")
public class MessageActorController extends AbstractController {

	@Autowired
	private MessageService	messageService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private FolderService	folderService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int folderId, final HttpServletRequest request) {
		ModelAndView result;
		final Folder f = this.folderService.findOne(folderId);
		Assert.isTrue(this.actorService.findByPrincipal().getFolders().contains(f));
		final Collection<Message> messages;
		Pageable pageable;
		Direction dir = null;
		Integer pageNum = 0;
		final String pageNumStr = request.getParameter(new ParamEncoder("row").encodeParameterName(TableTagParameters.PARAMETER_PAGE));
		final String sortAtt = request.getParameter(new ParamEncoder("row").encodeParameterName(TableTagParameters.PARAMETER_SORT));
		final String sortOrder = request.getParameter(new ParamEncoder("row").encodeParameterName(TableTagParameters.PARAMETER_ORDER));
		if (sortOrder != null)
			if (sortOrder.equals("1"))
				dir = Direction.ASC;
			else
				dir = Direction.DESC;
		if (pageNumStr != null)
			pageNum = Integer.parseInt(pageNumStr) - 1;
		if (sortAtt != null && dir != null)
			pageable = new PageRequest(pageNum, 5, dir, sortAtt);
		else
			pageable = new PageRequest(pageNum, 5);
		messages = this.messageService.findMessagesByFolderId(folderId, pageable);
		final Integer total = this.messageService.countMessagesByFolderId(folderId);
		result = new ModelAndView("message/list");
		result.addObject("messages", messages);
		result.addObject("total", total);
		result.addObject("folder", f);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		MessageForm messageForm;

		messageForm = new MessageForm();
		result = this.createEditModelAndView(messageForm);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView save(@Valid final MessageForm messageForm, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(messageForm);
		else
			try {
				final Message temp = this.messageService.create();
				temp.setSubject(messageForm.getSubject());
				temp.setPriority(messageForm.getPriority());
				temp.setBody(messageForm.getBody());
				boolean actorsExist = true;
				final Collection<Actor> rec = new ArrayList<Actor>();
				for (final String s : messageForm.getRecipients())
					if (s != null && !s.equals("")) {
						final Collection<Actor> recFound = this.actorService.findByUsername(s);
						if (recFound.isEmpty())
							actorsExist = false;
						else {
							final Actor a = new ArrayList<Actor>(recFound).get(0);
							rec.add(a);
						}
					}
				temp.setRecipients(rec);
				final Message message = this.messageService.reconstruct(temp);
				if (actorsExist) {
					final Folder outf = message.getFolder();
					this.messageService.save(message);
					result = new ModelAndView("redirect:list.do?folderId=" + outf.getId());
				} else
					result = this.createEditModelAndView(messageForm, "message.recipients.error");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(messageForm, "message.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int messageId) {
		ModelAndView result;
		final Message message = this.messageService.findOne(messageId);
		final Folder toDelete = message.getFolder();
		this.messageService.delete(message);
		result = new ModelAndView("redirect:list.do?folderId=" + toDelete.getId());
		return result;
	}

	@RequestMapping(value = "/move", method = RequestMethod.GET)
	public ModelAndView move(@RequestParam final int messageId) {
		ModelAndView result;
		final Message message = this.messageService.findOne(messageId);
		final Folder origin = message.getFolder();
		final Collection<Folder> available = new ArrayList<Folder>(this.actorService.findByPrincipal().getFolders());
		available.remove(origin);
		available.remove(this.folderService.findFolderByNameAndActor("Trash box"));
		available.remove(this.folderService.findFolderByNameAndActor("Spam box"));
		result = new ModelAndView("message/move");
		result.addObject("mess", message);
		result.addObject("origin", origin);
		result.addObject("folders", available);
		return result;
	}

	@RequestMapping(value = "/moveToFolder", method = RequestMethod.POST)
	public ModelAndView moveToFolder(final HttpServletRequest request) {
		ModelAndView result;
		final Message message = this.messageService.findOne(Integer.valueOf(request.getParameter("mess")));
		final Folder target = this.folderService.findOne(Integer.valueOf(request.getParameter("target")));
		Assert.notNull(target);
		this.messageService.moveToFolder(message, target);
		result = new ModelAndView("redirect:/message/actor/list.do?folderId=" + target.getId());
		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int messageId) {
		ModelAndView result;
		final Message message = this.messageService.findOne(messageId);
		final Collection<Folder> folders = new ArrayList<Folder>();
		for (final Folder fl : this.actorService.findByPrincipal().getFolders())
			folders.addAll(this.getAllAvailable(fl));
		boolean found = false;
		for (final Folder f : folders)
			if (f.getMessages().contains(message)) {
				found = true;
				break;
			}
		Assert.isTrue(found);
		this.messageService.checkMessage(messageId);
		result = new ModelAndView("message/display");
		result.addObject("mess", message);
		return result;
	}

	@RequestMapping(value = "/setchecked", method = RequestMethod.GET)
	public ModelAndView setChecked(@RequestParam final int messageId) {
		ModelAndView result;
		Assert.notNull(messageId);
		Assert.isTrue(messageId != 0);
		final Message message = this.messageService.findOne(messageId);
		final Folder f = message.getFolder();
		Assert.isTrue(f.getMessages().contains(message));
		Assert.isTrue(this.actorService.findByPrincipal().getFolders().contains(f));
		this.messageService.checkMessage(messageId);
		result = new ModelAndView("redirect:list.do?folderId=" + f.getId());
		return result;
	}

	@RequestMapping(value = "/markasspam", method = RequestMethod.GET)
	public ModelAndView markAsSpam(@RequestParam final int messageId) {
		ModelAndView result;
		Assert.notNull(messageId);
		Assert.isTrue(messageId != 0);
		final Message message = this.messageService.findOne(messageId);
		final Folder f = message.getFolder();
		Assert.isTrue(!f.getName().equals("Spam box") && !f.getName().equals("Out box"));
		Assert.isTrue(f.getMessages().contains(message));
		Assert.isTrue(this.actorService.findByPrincipal().getFolders().contains(f));
		this.messageService.markAsSpam(message);
		result = new ModelAndView("redirect:list.do?folderId=" + f.getId());
		return result;
	}

	protected ModelAndView createEditModelAndView(final MessageForm messageForm) {
		ModelAndView result;

		result = this.createEditModelAndView(messageForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final MessageForm messageForm, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("message/edit");
		result.addObject("messageForm", messageForm);
		result.addObject("message", messageCode);
		return result;
	}

	private Collection<Folder> getAllAvailable(final Folder f) {
		final Collection<Folder> res = new ArrayList<Folder>();
		res.add(f);
		if (!f.getChildren().isEmpty())
			for (final Folder fc : f.getChildren())
				res.addAll(this.getAllAvailable(fc));
		return res;
	}
}
