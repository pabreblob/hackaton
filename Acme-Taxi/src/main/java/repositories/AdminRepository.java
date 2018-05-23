
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.Admin;
import domain.Driver;
import domain.Mechanic;
import domain.User;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

	@Query("select a from Admin a where a.userAccount.id = ?1")
	Admin findAdminByUserAccountId(int UserAccountId);

	@Query("select max(a.attendants.size) from Announcement a")
	Integer getMaxAttendants();

	@Query("select min(a.attendants.size) from Announcement a")
	Integer getMinAttendants();

	@Query("select avg(a.attendants.size) from Announcement a")
	Double getAvgAttendants();

	@Query("select sqrt(sum(a.attendants.size * a.attendants.size)/count(a.attendants.size)-(avg(a.attendants.size)*avg(a.attendants.size))) from Announcement a")
	Double getStandardDeviationAttendants();

	@Query("select u from User u where u.userAccount.banned is false order by u.meanRating desc")
	Collection<User> getTopUsers();

	@Query("select d from Driver d where d.userAccount.banned is false order by d.meanRating desc")
	Collection<Driver> getTopDrivers();

	@Query("select distinct rs.mechanic from RepairShop rs where rs.mechanic.userAccount.banned is false order by rs.meanRating desc")
	Collection<Mechanic> getTopMechanics();

	@Query("select u from User u where u.userAccount.banned is false order by u.meanRating asc")
	Collection<User> getWorstUsers();

	@Query("select d from Driver d where d.userAccount.banned is false order by d.meanRating asc")
	Collection<Driver> getWorstDrivers();

	@Query("select distinct rs.mechanic from RepairShop rs where rs.mechanic.userAccount.banned is false order by rs.meanRating asc")
	Collection<Mechanic> getWorstMechanics();

	@Query("select (select count(a) from Announcement a where a.cancelled is true)*1.0/count(ann) from Announcement ann")
	Double getRatioCancelledAnnouncements();

	@Query("select r.creator from Report r where r.creator.userAccount.banned is false group by r.creator order by count(r) desc")
	Collection<Actor> getMostReportsWritten();

	@Query("select r.reported from Report r where r.reported.userAccount.banned is false group by r.reported order by count(r) desc")
	Collection<Actor> getMostReportsReceived();
}
