//backend restful calls and service logic
import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { catchError, map } from 'rxjs/operators';
import { Observable, throwError } from 'rxjs';
import { identifierModuleUrl } from '@angular/compiler';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  reservationurl: string = "http://localhost:8080/flightreservation/reservations/"

  constructor(private _http: HttpClient) { }

  public getReservation(id: number): any {
    //Call flightreservation app
    return this._http.jsonp(this.reservationurl + id, 'callback')
      .pipe(catchError(err => {
        return throwError(err);
      })).subscribe(err => console.log("HTTP ERROR", err));

  }

  public checkin(checkInRequest:any):any {
    
    return this._http.post(this.reservationurl, checkInRequest).pipe(catchError(err => {
      return throwError(err);
    })).subscribe(err => console.log("HTTP ERROR", err));




  }
}


