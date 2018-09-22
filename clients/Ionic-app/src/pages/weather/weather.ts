import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';
import 'rxjs/add/operator/map';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import { Observable } from 'rxjs/Observable';

@Component({
  selector: 'page-weather',
  templateUrl: 'weather.html'
})
export class Weather {

  nestTemperature: string;
  nestHumidity: string;
  private url = 'https://developer-api.nest.com/devices/thermostats/-3oBcfZj1K-7fPnj632cKFfl0ewfyRqE';
  private token:string = 'Bearer c.hopTLb3vBnYLGYZY5vv1p6RgGwtFFmEPSXrMnkNqZ5WShJiFyBbwghVplVgDbZiaFyuIik4TicHFSNQ72cgm9ksBntV7a3zVGGRFMXYoNxcLRyN7QrnqFFWTwgLp5ft2k7IHFpTgqo8f7tih';
  private headers = new HttpHeaders();

  //private headers = {'Content-Type': 'application/json', 'Authorization': this.token, 'Cache-Control': 'no-cache' };
  // constructor(public navCtrl: NavController, private http:Http) {
  //   //this.getNestTemp();
  //   this.getNestTemp().subscribe(
  //   temperatures => {
  //     //this.temperature = temperatures[0].temp;
  //     //this.humidity = temperatures[0].hum;
  //     this.nestTemperature = temperatures[0].temp;
  //   },
  //   error => console.log('Oh no, something went wrong with the nest http request :( '));
  // console.log(this.nestTemperature);
  // }

  // private getNestTemp(): Observable<any>{
  //   console.log(this.options);
  //     return this.http.get(this.url, this.options).map(res => res.json());
  // }


  constructor(public navCtrl: NavController, private http:HttpClient) {
    //this.getNestTemp();
    //{'Content-Type': 'application/json', 'Authorization': this.token, 'Cache-Control': 'no-cache' }
    this.headers.append('Content-Type', 'application/json');
    this.headers.append('Authorization', this.token);
    this.headers.append('Cache-Control', 'no-cache');
    this.getNestTemp().subscribe(
    temperatures => {
      this.nestHumidity = temperatures[0].hum;
      this.nestTemperature = temperatures[0].temp;
    },
    error => console.log('Oh no, something went wrong with the nest http request :( '));
  console.log(this.nestTemperature);
  }

  private getNestTemp(): Observable<any>{
    console.log(this.headers);
      return this.http.get(this.url, {headers: this.headers}).map(res => {
          console.log(res);
        }
        
      );
  }
}
