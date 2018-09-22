var express = require('express');
var router = express.Router();
var mqtt = require('mqtt'), url = require('url');
var mqtt_url = url.parse(process.env.CLOUDMQTT_URL || 'mqtt://localhost:1884');

const client = mqtt.connect(mqtt_url)

client.on('connect', function () {
  client.subscribe('smarthome/light')
  console.log("MQTT CONNECTED")
})

var status;
router.post('/', function(req, res, next) {
  if(req.body.status == 'off') {
    status = "0";
    client.publish('smarthome/light', status)
    
    res.send({status: status})
  } else if(req.body.status == 'on'){
    status = "1";
    client.publish('smarthome/light', status)
    
    res.send({status: status})
  }
});


router.get('/', function(req, res){
  res.send({status: status});
})


module.exports = router;

