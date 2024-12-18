import { Component, OnInit } from '@angular/core';
import { MatSelectChange } from '@angular/material/select';
import { User } from '../../../model/user';
import { AdminService } from '../../services/admin.service';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { CreateEquipmentAssignUserRequest } from '../../../model/equipment-create-assign-user';
import { Equipment } from '../../../model/equiment';

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

  constructor(private adminSerice: AdminService, private fb: FormBuilder){
    this.createEquipmentAndAssignToUserForm = this.fb.group({
      description: [''],
      serialNumber: [''],
      brand: [''],
      model: [''],
      user: [''],
      category: [''],
      unity: [''], 
    })
    
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
          console.log("Sucessfull response", res)
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
          console.log("Sucessfull response" + res.userEquipments)
        }), (error: any) => {
          console.log(error)
        }
      }
    }
  }
}
