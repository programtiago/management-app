import { Component, OnInit } from '@angular/core';
import { MatSelectChange } from '@angular/material/select';
import { User } from '../../../model/user';
import { AdminService } from '../../services/admin.service';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { CreateEquipmentAssignUserRequest } from '../../../model/equipment-create-assign-user';
import { Router } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-equipment-form',
  templateUrl: './equipment-form.component.html',
  styleUrl: './equipment-form.component.scss'
})
export class EquipmentFormComponent implements OnInit{

  selectedValueCreationAssignmentType: any
  selectedValueDepartment: any
  selectedUserId: any
  selectedCategory: any
  selectedWorkstation: any
  typeAssignmentSelected: string = '';

  users: User[] = [];

  createEquipmentForm!: FormGroup;
  createEquipmentAndAssignToUserForm!: FormGroup;

  categorys: string[] = ['Scanner', 'Desktop', 'Printer', 'Dockstation', 'Screen', 'Laptop']
  workstations: string[] = ['Unity A', 'Unity B', 'Unity C']

  constructor(private adminSerice: AdminService, private fb: FormBuilder, private router: Router, private location: Location, ){
    //Form to send the data equipment with assignment 
    this.createEquipmentAndAssignToUserForm = this.fb.group({
      description: [''],
      serialNumber: [''],
      brand: [''],
      model: [''],
      user: [''],
      category: [''],
      unity: [''], 
    })
    
    //Form to send the data equipment only with no assign
    this.createEquipmentForm = this.fb.group({
      description: [''],
      serialNumber: [''],
      brand: [''],
      model: [''],
      category: [''],
      unity: [''], 
    })
  }

  ngOnInit(): void {
    this.adminSerice.listUsers().subscribe((res) => {
      this.users = res;
    })
  }

  valueChoosedChanged(event: MatSelectChange){
    this.selectedValueCreationAssignmentType = {
      value: event.value,
      text: event.source.triggerValue
    };
    this.selectedValueCreationAssignmentType = this.selectedValueCreationAssignmentType.text;
  }

  valueDepartmentSelectedChanged(event: MatSelectChange){
    this.selectedValueDepartment = {
      value: event.value,
      text: event.source.triggerValue
    }
    this.selectedValueDepartment = this.selectedValueDepartment.text
  }

  createEquipment(){
    const newEquipment: CreateEquipmentAssignUserRequest = this.createEquipmentForm.value;
    if (this.selectedValueCreationAssignmentType == 'None'){
      if (this.createEquipmentForm.valid){
        this.adminSerice.createEquipmentWithNoAssign(newEquipment).subscribe((res) => {
          if (res != null){
            this.router.navigateByUrl("admin/equipments")
          }
        }), (error: any) => {
          console.log(error)
        }
      }
    }
  }
  
  createEquipmentAndAssignToUser(userId: number){
    const newEquipment: CreateEquipmentAssignUserRequest = this.createEquipmentAndAssignToUserForm.value;
    if (this.selectedValueCreationAssignmentType == 'User'){
      if (this.createEquipmentAndAssignToUserForm.valid){
        this.adminSerice.createEquipmentAndAssignToUser(newEquipment, userId).subscribe((res) => {
          if (res != null){
            this.router.navigateByUrl("admin/equipments")
          }
        }), (error: any) => {
          console.log(error)
        }
      }
    }
  }

  onCancel(){
    this.location.back();
  }
}
