## Design decisions
* No debug log guarding - good practice to handle this but far too much for this application
* Service layer added to split functionality - was not there in first place because the application size, but when testing it makes sense to split this.
* Logs for method entry and exits on info level - creates a nice readable story of application activity
* Application should not throw logs - all exceptions should result in HTTP responses
* MockRestServiceServer is used to be able to interact with the mock and verify the amount of usages inside a service