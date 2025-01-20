# Gestion-de-Recrutement 
 Une application Web JEE avec les technologies Servlet JSP MVC JSTL JDBC ORM ou Spring MVC


```bash
pull mysql
``` 
```bash
docker run -p 3307:3306 --name mysqlcontainer -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=RECRUTMENT -d mysql
``` 

```bash
docker exec -it mysqlcontainer bash
``` 

```bash
bash-5.1# mysql -u root -p
``` 

```bash
Enter password:root
``` 

```bash
mysql> use RECRUTMENT;
``` 
