#
# Build stage
#
FROM maven:3.6.3-openjdk-8
ARG workdir=/home/app
ENV workdir=${workdir}
COPY src ${workdir}/src
COPY pom.xml ${workdir}
COPY entrypoint.sh ${workdir}
RUN mvn -f ${workdir}/pom.xml clean package
# COPY config /usr/local/lib/
ARG app_folder=/usr/local/lib
ENV app_folder=${app_folder}
#COPY ${workdir}/target/turnero-medico-0.0.1-SNAPSHOT.jar ${app_folder}/app.jar
RUN chmod 755 ${workdir}/target/turnero-medico-0.0.1-SNAPSHOT.jar ${workdir}/entrypoint.sh
#ENTRYPOINT ["sh", "-c", "sleep 2073600"]
#RUN java -jar ${workdir}/target/turnero-medico-0.0.1-SNAPSHOT.jar
ENTRYPOINT /home/app/entrypoint.sh
#ENTRYPOINT ["java","-jar",${workdir}"/target/turnero-medico-0.0.1-SNAPSHOT.jar"]