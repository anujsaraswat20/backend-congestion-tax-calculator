# Refactoring Java

## step 1: 
check port in "application.properties" file and change if required

## step 2: 
run command: mvn clean install

## step 3: 
After successful compile, go to "target" folder and run "java -jar JAR_FILE_NAME"

## Get all Tool free vehicles

## URL: GET- localhost:4000/api/v1/listTollFreeVehicle
Request Header:    x-admin-key =  <admin-key>

Response:
    {
    "message": "Returned successfully from listToolFreeVehicle() with response -> [MILITARY, EMERGENCY, FOREIGN, BUSSES, MOTORCYCLE, DIPLOMAT]",
    "error": null
    }

## Add new Toll free vehicle

## URL: POST - localhost:4000/api/v1/addTollFreeVehicle

Request:
    [
        {
            "vehicleType": "ABC"
        },
        {
            "vehicleType": "ABC-3"
        }
    ]
Response:
    [
        "MILITARY",
        "EMERGENCY",
        "ABC",
        "FOREIGN",
        "BUSSES",
        "MOTORCYCLE",
        "ABC-3",
        "DIPLOMAT"
    ]

## Get congestion fee 

## URL: POST - localhost:4000/api/v1/congestionTaxFee

Request:
    {
        "vehicleType": "Car",
        "dates": [
            "2013-01-14T08:00:00",
            "2013-01-15T09:40:00"
        ]
    }

Response:

    {
        "message": "Total Congestion tax -> 21",
        "error": null
    }



