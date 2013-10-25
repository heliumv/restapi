1) Bei einem Update:
a) Das Verzeichnis "./lib" auf das Verzeichnis (beispielsweise)
   /apache-tomcat-7.0.42 inklusive der Struktur kopieren.


2) Bei der Erstinstallation zusaetzlich:
a) Das Verzeichnis "./conf" auf das Verzeichnis (beispielsweise)
   /apache-tomcat-7.0.42 inklusive der Struktur kopieren.
b) Die Datei /apache-tomcat-7.0.42/conf/Catalina/localhost/restapi.xml
   oeffnen und den Hostname des HELIUM V Servers in 
   "Environment name="java.naming.provider.url" eintragen
c) Die Datei /apache-tomcat-7.0.42/conf/server.xml oeffnen und
   den Port "8080" durch "8280" ersetzen. Das kommt insgesamt 2 mal vor.
   