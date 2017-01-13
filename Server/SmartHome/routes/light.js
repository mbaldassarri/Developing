var express = require('express');
var router = express.Router();

const mqtt = require('mqtt')
const client = mqtt.connect('http://insert-mqtt-broker-address-here:8883', {username: 'username', password: 'password'})
//const client = mqtt.connect('mqtt://test.mosquitto.org')


client.on('connect', function () {
  client.subscribe('smarthome/light')
  console.log("OK CONNECTED")
})

var status;
/* GET users listing. */
router.post('/', function(req, res, next) {
  if(req.body.status == '1') {
    console.log("send status to arduino--> " + req.body.status)
    client.publish('smarthome/light', '1')
    status = 1;
    res.send({status: status})
  } else {
    console.log("send status to arduino--> " + req.body.status)
    client.publish('smarthome/light', '0')
    status = 0;
    res.send({status: status})
  }
});


router.get('/', function(req, res){
  res.send({status: status});
})


module.exports = router;
