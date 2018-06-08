-----Regarding the database-----
The name of the database used in this deliverable is "Acme-Taxi".

-----Regarding tests executed in the pre-production configuration-----
Inside the Item 6 and 7 folders there is a database creation script with the data used during the performance
and acceptation tests.

-----Regarding our A+-----
Starting June 11, Google Maps will require to have a Google Cloud Billing Account. We have been warned against the problems that may arise 
if we give our credit card information, so we do not have a Google Cloud Billing Account. This means that this A+ will no longer work after 
June 11. In order to test the page after June 11, it is recommended to log in as admin and in the configuration view, disable the use of the API. 
By doing so, the system will provide false information when testing the calculation of price, distance and time, but all the request.
If performance tests are executed before June 11, please disable the usage of the API, otherwise, when executing tests related to request,
the maximum rate of uses of the API may be exceeded.