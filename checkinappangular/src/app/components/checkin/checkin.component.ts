import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import { DataService } from 'src/app/services/data.service';
import { catchError, map } from 'rxjs/operators';

@Component({
  selector: 'app-checkin',
  templateUrl: './checkin.component.html',
  styleUrls: ['./checkin.component.css']
})
//receive id and fetch reservation
export class CheckinComponent implements OnInit {
  data:any;
  noOfBags:any;
  checkInResponse:any;
  
  constructor(private router:Router, private route:ActivatedRoute, private service:DataService) { }

  ngOnInit() {
    var id = Number.parseInt(this.route.params.pipe(map(p => p.id)).toString());
    this.service.getReservation(id).subscribe((res: any)=> {
        this.data = res;
    })
  }
  checkin(noOfBags:any){
    var checkInRequest:any = new Object();
    checkInRequest.id = this.data.id;
    checkInRequest.checkedIn = true;
    checkInRequest.numberOfBags = noOfBags;

    this.service.checkin(checkInRequest).subscribe((res: any)=> {
      this.checkInResponse = res;
  })
  this.router.navigate(['/confirm']);

  }

}
