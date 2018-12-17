import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import { Headers, RequestOptions, Http } from '@angular/http';

@Injectable()
export class DeviceService {
  
  private headers = new Headers({ 'Content-Type': 'application/json', 'charset': 'UTF-8' });
  private options = new RequestOptions({ headers: this.headers });
  private url: string = "https://casa-dashboard.herokuapp.com/light";
  constructor(private http: Http) { }

  changeLampStatus(lampStatus): Observable<any> {
    return this.http.post(this.url, JSON.stringify({
      status: lampStatus ? '1' : '0'
    }), this.options).map(res => res.json());
  }

  getLampStatus(): Observable<any> {
    return this.http.get(this.url).map(res => res.json());
  }

}
