import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';
import {DeviceService} from "../../app/services/device.service";

@Component({
  selector: 'page-devices',
  templateUrl: 'devices.html'
})
export class Devices {
  state = 'ON/OFF'
  constructor(public navCtrl: NavController, private deviceService: DeviceService) {

  }
  
  switchOnLamp(){
    this.deviceService.changeLampStatus().subscribe(data => {
      debugger;
      console.log("data " + data); this.state = data
    });
  }

}
