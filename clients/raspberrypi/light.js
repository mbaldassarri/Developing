var Gpio = require('onoff').Gpio;
var lamp = new Gpio(14, 'high');

var mqtt = require('mqtt'), url = require('url');
var mqtt_url = url.parse("mqtt://zbqlnwjk:bF4CAJfM5AGI@m15.cloudmqtt.com:12505");
const client = mqtt.connect(mqtt_url)

client.on('connect', function () {
  client.subscribe('smarthome/light')
  console.log("OK CONNECTED")
})

client.on('message', function (topic, message) {
  
  if(topic == 'smarthome/light'){
    console.log(message.toString());
    if(message == '0'){
      lamp.writeSync(0);
    }
    if(message == '1'){
      lamp.writeSync(1);
    }
  }
})


process.on('SIGINT', function () {
  lamp.unexport();
  client.end()
});