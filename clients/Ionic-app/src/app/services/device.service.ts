import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import { Headers, RequestOptions, Http } from '@angular/http';

@Injectable()
export class DeviceService {


  private headers = new Headers({ 'Content-Type': 'application/json', 'charset': 'UTF-8' });
  private options = new RequestOptions({ headers: this.headers });
  private url: string = "https://casa-dashboard.herokuapp.com/light";
  //private url: string = "https://jsonplaceholder.typicode.com/posts";
  //private url: string = "http://localhost:3000/light";

  constructor(private http: Http) { }

  changeLampStatus(): Observable<any> {
    return this.http.post(this.url, JSON.stringify({
      status: 'off'
    }), this.options);
  }

}
