import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';
import {DeviceService} from "../../app/services/device.service";

@Component({
  selector: 'page-devices',
  templateUrl: 'devices.html'
})
export class Devices {
  lampStatus: boolean = false;
  constructor(public navCtrl: NavController, private deviceService: DeviceService) {
    this.deviceService.getLampStatus().subscribe(res => this.lampStatus = this.parseStatus(res.status));
  }
  
  switchOnLamp(){
    this.deviceService.changeLampStatus(this.lampStatus)
                      .subscribe(
                        res => {
                                this.lampStatus = this.parseStatus(res.status);
                              }, 
                        error => console.log(error));
  }

  private parseStatus(status: string) {
    return status == '0' ? false : true;
  }
}
