var Gpio = require('onoff').Gpio;
var lamp = new Gpio(14, 'high');

var mqtt = require('mqtt'), url = require('url');
var mqtt_url = url.parse("mqtt://zbqlnwjk:bF4CAJfM5AGI@m15.cloudmqtt.com:12505");
const client = mqtt.connect(mqtt_url)
const topic_current_status = 'smarthome/status';
const topic_change_status = 'smarthome/light';

client.on('connect', function () {
  client.subscribe('smarthome/light')
  console.log('Current status: ', lamp.readSync() == 0 ? 'OFF' : 'ON');
})

client.on('message', function (topic, message) {
  if(topic == topic_current_status) {
    client.publish('smarthome/status', lamp.readSync())
  }
  if(topic == topic_change_status){
    if(message != lamp.readSync()){
      lamp.writeSync(lamp.readSync() ^ 1)
    } 
  }
})

process.on('SIGINT', function () {
  lamp.unexport();
  client.end()
});