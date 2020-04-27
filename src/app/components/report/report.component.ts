import { Component, OnInit } from '@angular/core';
import {MachineService} from "../../services/machine.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.css']
})
export class ReportComponent implements OnInit {
  constructor(private machineService: MachineService, private route: ActivatedRoute) { }

  public machineData;
  public runtimeData;
  public param;

  ngOnInit(): void {
    this.param = this.route.snapshot.queryParams["date"];

    this.getMachineData(this.route.snapshot.params.name, this.param);
    this.getRuntimeData(this.param);
  }

  getMachineData(name: string, date: string){
    this.machineService.getTotalValueOf(name, date).subscribe(
       data => { this.machineData = data},
        err => { console.error(err)},
      () => { console.log("done!")}
    );
  }

  getRuntimeData(date: string){
    this.machineService.getRuntime(date).subscribe(
      data => { this.runtimeData = data},
      err => { console.error(err)},
      () => { console.log("done!")}
    );
  }



}
