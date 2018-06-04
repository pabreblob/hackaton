start transaction;

use `Acme-Taxi`;

revoke all privileges on `Acme-Taxi`.* from 'acme-user'@'%';
revoke all privileges on `Acme-Taxi`.* from 'acme-manager'@'%';

drop user 'acme-user'@'%';
drop user 'acme-manager'@'%';

drop database`Acme-Taxi`;

commit;

