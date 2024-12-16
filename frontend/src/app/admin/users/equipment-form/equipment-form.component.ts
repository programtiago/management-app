import { Component, OnInit, ViewChild, viewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatSelect, MatSelectChange } from '@angular/material/select';
import { User } from '../../../model/user';
import { Observable } from 'rxjs';
import { AdminService } from '../../services/admin.service';

@Component({
  selector: 'app-equipment-form',
  templateUrl: './equipment-form.component.html',
  styleUrl: './equipment-form.component.scss'
})
export class EquipmentFormComponent implements OnInit{

  selectedValueCreationWithAssignment: any;
  selectedValueDepartment: any;
  selectedUserId: any;
  users: User[] = [];

  constructor(private adminSerice: AdminService){}

  ngOnInit(): void {
    this.adminSerice.listUsers().subscribe((res) => {
      this.users = res;
    })
  }

  valueChoosedChanged(event: MatSelectChange){
    this.selectedValueCreationWithAssignment = {
      value: event.value,
      text: event.source.triggerValue
    };
    this.selectedValueCreationWithAssignment = this.selectedValueCreationWithAssignment.text;
  }

  valueDepartmentSelectedChanged(event: MatSelectChange){
    this.selectedValueDepartment = {
      value: event.value,
      text: event.source.triggerValue
    }
    this.selectedValueDepartment = this.selectedValueDepartment.text
  }

  checkId(){
    console.log(this.selectedUserId)
  }

  create(){
    this.adminSerice.getUserById(this.selectedUserId).subscribe((res) => {
      console.log(res)
    })
  }


}
