# Coding Challenge - Weather API

## Technologies used

- Java 17
- Spring Boot 3.3.5
- MySql
- Docker
- Maven

### Running the Application

#### Prerequisites

Ensure you have the following installed:
- Docker Desktop

#### How to run

1. Build and run the application from the root directory of the project using Docker Compose:
```bash
> docker-compose run
```
2. Check docker containers are running:
````bash
> docker ps
CONTAINER ID        IMAGE                    COMMAND                  CREATED             STATUS              PORTS                               NAMES
2f69b8ee475f        weatherapi_api_service   "java -jar /home/app…"   10 minutes ago      Up 10 minutes       0.0.0.0:8080->8080/tcp              weatherapi_api_service_1
6b3edfead7ea        mysql:8.0.13             "docker-entrypoint.s…"   10 minutes ago      Up 10 minutes       0.0.0.0:3306->3306/tcp, 33060/tcp   weatherapi_mysqldb_1
````

The application will be available at `http://localhost:8080`

### Sample request
```bash
curl -X POST http://localhost:8080/weather/metrics/ -H "Content-Type:application/json" -d
'{
    "sensors": [1],
    "metrics": ["temperature", "humidity"],
    "dateRange": {
        "start": "2025-04-02",
        "end": "2025-04-09",
    },
    "statistic": "average"
}'
```
##### The requisites for the request body are:
- Mandatory fields: sensors, metrics, statistic
- Sensors: List of sensor IDs (e.g., [1, 2])
- Metrics: must contain only the following values: temperature, humidity, wind_speed, pressure
- Statistic: must contain only the following values: average, min, max, sum
- Date-range: The date format must be yyyy-MM-dd

### Sample response
```json
[
    {
        "id": null,
        "sensorId": null,
        "date": null,
        "temperature": 12.0,
        "humidity": 82.0,
        "wind_speed": 0.0,
        "precipitation": 0.0
    }
]
```

### Points of improvement

- Parse query response to return only the requested metrics and exclude null values
- Add extra error handling for query results
- Add extra unit tests