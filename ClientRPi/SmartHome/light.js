const mqtt = require('mqtt');
var Gpio = require('onoff').Gpio;
var lamp = new Gpio(18, 'high');

const client = mqtt.connect('http://insert-mqtt-broker-address-here:8883', {username: 'username', password: 'password'})

client.on('connect', function () {
  client.subscribe('smarthome/light')
  console.log("OK CONNECTED")
})

client.on('message', function (topic, message) {
  if(topic == 'smarthome/light'){
	console.log(message.toString());
    if(message == '1'){
      lamp.writeSync(0);
    }
    if(message == '0'){
      lamp.writeSync(1);
    }
  }
  console.log(message.toString())
})


process.on('SIGINT', function () {
  lamp.unexport();
  client.end()
});
