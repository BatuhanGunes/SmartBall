#include <SoftwareSerial.h>

SoftwareSerial bt_contact(0,1); // RX, TX

int pirPin = 9; // PIR pin
int deger = 0;
 
void setup() {
pinMode(pirPin, INPUT); // PIR Pin'i giriş yapılıyor
Serial.begin(9600); //Serial Porttan veri göndermek için baundrate ayarlanıyor.

//Serial.println("Start");
}
 
void loop(){
deger = digitalRead(pirPin); // Dijital pin okunuyor

if (deger == HIGH) {
    Serial.println("A");
    delay(4000);
}
}











