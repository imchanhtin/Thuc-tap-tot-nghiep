#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>
#include <DHT.h>
#include <MQ2.h>

//khai báo cảm biến nhiệt
#define DHTPIN 2      
#define DHTTYPE DHT22  
DHT dht(DHTPIN, DHTTYPE);

//Khai báo cảm biến khí MQ2
int pin = A0;
int lpg, co, smoke;
MQ2 mq2(pin);

//Khai báo Thingspeak
const int channelID = 478199;
String writeAPIKey = "EB73VMZLI139YU5S"; 
const char* server = "api.thingspeak.com"; 

//Khai báo Firebase
#define FIREBASE_HOST "canhbaochay.firebaseio.com"
#define FIREBASE_AUTH "g5O5zsy04C10h0X0j9QZhXIW40OzRp7OojsYr33G"

//Khai báo Wi-Fi
#define WIFI_SSID "thixinhdep"
#define WIFI_PASSWORD "thixinhdep"
WiFiClient client;


void setup() {
  Serial.begin(9600);
  mq2.begin();
  //kết nối Wi-Fi
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("connecting");
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(500);
  }
  Serial.println();
  Serial.print("Kết nối thành công, IP: ");
  Serial.println(WiFi.localIP());
  
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
}

int n = 0;

void loop() {
  float gas = mq2.readLPG();
  float co = mq2.readCO();
  float khoi = mq2.readSmoke();
  
  float nhietdo = dht.readTemperature();
  float doam = dht.readHumidity();
  if (isnan(nhietdo) || isnan(doam)) {
         Serial.println("Cảm biến lỗi!");
         return;
  }

  //Gửi dữ liệu về Thingspeak
  if (client.connect(server, 80)) {
                // Construct API request body
                String body = "field1=" + String(nhietdo, 1) + "&field2=" + String(doam, 1)+ "&field3=" + String(gas, 1)+ "&field4=" + String(co, 1);

                client.print("POST /update HTTP/1.1\n");
                client.print("Host: api.thingspeak.com\n");
                client.print("Connection: close\n");
                client.print("X-THINGSPEAKAPIKEY: " + writeAPIKey + "\n");
                client.print("Content-Type: application/x-www-form-urlencoded\n");
                client.print("Content-Length: ");
                client.print(body.length());
                client.print("\n\n");
                client.print(body);
                client.print("\n\n");
        }
        client.stop();

  //Gửi dữ liệu về Firebase
  Firebase.setFloat("nhietdo", nhietdo);
  Firebase.setFloat("doam",doam);
  
  Firebase.setFloat("gas",gas);
  Firebase.setFloat("khoi",khoi);
  Firebase.setFloat("CO",co);
  
  //Hiển thị lỗi nếu có
  if (Firebase.failed()) {
      Serial.print("setting /number failed:");
      Serial.println(Firebase.error());  
      return;
  }

  //Hiển thị dữ liệu ra console
  Serial.println("=================");
  Serial.print("nhietdo: ");
  Serial.println(Firebase.getFloat("nhietdo"));
  Serial.print("doam: ");
  Serial.println(Firebase.getFloat("doam"));
    Serial.print("GAS: ");
  Serial.println(Firebase.getFloat("gas"));
    Serial.print("Khoi: ");
  Serial.println(Firebase.getFloat("khoi"));
    Serial.print("CO: ");
  Serial.println(Firebase.getFloat("CO"));
  delay(500);
}
