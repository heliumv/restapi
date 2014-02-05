1) Tomcat beenden (service hvtomcat stop bzw. Dienst HvTomcat beenden)  

2) Bei einem Update oder der Erstinstallation:
a) Das Verzeichnis "./lib" auf das Verzeichnis (beispielsweise)
   /apache-tomcat-7.0.42 inklusive der Struktur kopieren.
b) Die Dateien restapi.war, restapi-doc.war und ROOT.war auf das Verzeichnis
   (beispielsweise) /apache-tomcat-7.0.42/webapps kopieren
c) Die Verzeichnisse restapi, restapi-doc und ROOT löschen 
   (/apache-tomcat-7.0.42/, es reicht nicht, diese Verzeichnis nur zu leeren)

3) Bei der Erstinstallation zusaetzlich:
a) Das Verzeichnis "./conf" auf das Verzeichnis (beispielsweise)
   /apache-tomcat-7.0.42 inklusive der Struktur kopieren.
b) Die Datei /apache-tomcat-7.0.42/conf/Catalina/localhost/restapi.xml
   oeffnen und den Hostname des HELIUM V Servers in 
   "Environment name="java.naming.provider.url" eintragen
c) Die Datei /apache-tomcat-7.0.42/conf/server.xml oeffnen und
   den Port "8080" durch "8280" ersetzen. Das kommt insgesamt 2 mal vor.
   