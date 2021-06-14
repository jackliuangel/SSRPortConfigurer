`SSR port Configurer` 
` ver 0.1` 

# Why：

Sometimes the V2Ray service or SSR service are blocked but actually these services are still available on Cloud(GCP or AWS), with just the port are blocked.

So this is a web service to config the port without logging in Cloud Console to change.

# How it works:

With Spring security, it requires authentication and authorization to view and config the port.

In local, with postgreSQL (in docker), the authentication is done by Database as datasource.

In prod, the authentication is done by InMemeory service.

After authencation and authorization, it will show and config the V2Ray/SSR config file based on the config, and then restart the V2Ray/SSR service.

`SSR.Path(SSR.Command) `

`V2Ray.Path(V2Ray.Command)`

# How to Run:

In local, run with idea and with postgreSQL (in docker)docker.

`docker run --name VPN-port-service-db -e POSTGRES_PASSWORD=admin -e POSTGRES_USER=admin -p 5432:5432 -d postgres`

In prod, run by command

`java -jar *.jar`
