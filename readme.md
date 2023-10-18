# Loan Service

This project is a Java-based loan service application.

## Steps to Run the Project

Follow these steps to run the project:

1. Prerequisites 
   1. Install Java 11
   ```bash
      java -version   
      openjdk version "11.0.20.1" 2023-08-24
      OpenJDK Runtime Environment (build 11.0.20.1+1-post-Ubuntu-0ubuntu122.04)
      OpenJDK 64-Bit Server VM (build 11.0.20.1+1-post-Ubuntu-0ubuntu122.04, mixed mode, sharing)
    ```
   2. Install Maven
    ```bash
        mvn -version
        Apache Maven 3.6.3
        Maven home: /usr/share/maven
        Java version: 11.0.20.1, vendor: Ubuntu, runtime: /usr/lib/jvm/java-11-openjdk-amd64
        Default locale: en_IN, platform encoding: UTF-8
        OS name: "linux", version: "6.2.0-34-generic", arch: "amd64", family: "unix"
    ```
   
2. Steps to run loan service application:

   ```bash
   git clone https://github.com/tarunjainsagar/LoanService.git
   cd LoanService
   mvn clean package
   java -jar target/loan-0.0.1-SNAPSHOT.jar
    ```
   
   3. Sample Api Calls:
      1. Fee Projections Api Request
      ```bash
      curl -X POST -H "Content-Type: application/json" -d '{
       "amount": 3000,
       "duration": 3,
       "startDate": "2023-11-01",
       "durationType": "WEEKLY",
       "installmentFrequency": "WEEKLY", "showDetails": true
       }' http://localhost:8080/loan/feeProjections
      ```
      2. Installment Projections Api Request
      ```bash
      curl -X POST -H "Content-Type: application/json" -d '{
       "amount": 3000,
       "duration": 3,
       "startDate": "2023-11-01",
       "durationType": "WEEKLY",
       "installmentFrequency": "WEEKLY", "showDetails": true
       }' http://localhost:8080/loan/feeProjections
      ```
       3. View Query/Request History Request
      ```bash
        curl -X GET "http://localhost:8080/loan/getQueryHistory?fromDate=2023-10-18&toDate=2023-10-18"
       ```
   
      4. Sample Responses:
         1. Fee Projections Api Response
         ```bash
         {
            "status": "200",
            "message": "Success",
            "data": {
               "actual_loan_amount": 3000,
               "no_of_installments": 3,
               "durationType": "WEEKLY",
               "installmentFrequency": "WEEKLY",
               "Projections": [
                  {
                     "date": "2023-11-01",
                     "amount": 30.0,
                     "remark": "Interest: 30.0, Service Fees: 0.0"
                  },
                  {
                     "date": "2023-11-08",
                     "amount": 45.0,
                     "remark": "Interest: 30.0, Service Fees: 15.0"
                  },
                  {
                     "date": "2023-11-15",
                     "amount": 30.0,
                     "remark": "Interest: 30.0, Service Fees: 0.0"
                  }
               ],
               "projections_type": "/feeProjections"
            }
         }
         ```
         2. Installment Projections Api Response
         ```bash
         {
            "status": "200",
            "message": "Success",
            "data": {
               "actual_loan_amount": 3000,
               "no_of_installments": 3,
               "durationType": "WEEKLY",
               "installmentFrequency": "WEEKLY",
               "Projections": [
                  {
                     "date": "2023-11-01",
                     "amount": 1030.0,
                     "remark": "Interest: 30.0, Service Fees: 0.0, Principal Installment: 1000"
                  },
                  {
                     "date": "2023-11-08",
                     "amount": 1045.0,
                     "remark": "Interest: 30.0, Service Fees: 15.0, Principal Installment: 1000"
                  },
                  {
                     "date": "2023-11-15",
                     "amount": 1030.0,
                     "remark": "Interest: 30.0, Service Fees: 0.0, Principal Installment: 1000"
                  }
               ],
               "projections_type": "/installmentProjections"
            }
         }
         ```