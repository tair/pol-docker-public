From openjdk:17
CMD ["mvn","clean","package"]
copy ./target/paywall-2.0.0.jar paywall-2.0.0.jar
CMD ["java","-jar","paywall-2.0.0.jar"]
EXPOSE 8090