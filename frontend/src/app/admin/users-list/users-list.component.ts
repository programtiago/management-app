import { Component, OnInit } from '@angular/core';
import { User } from '../../model/user';

@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrl: './users-list.component.scss'
})
export class UsersListComponent implements OnInit{

  displayedColumns: String[] = ['firstName', 'lastName', 'workNumber', 'department', 'registryDate', 'isActive', 
    'userRole',  'email', 'contactNumber', 'updatedAt'
  ]
  users: User[] = [
    { _id: '1', firstName: 'Tiago', lastName: 'Silva', workNumber: 30035, department: 'IT', registryDate: '15/11/2021', isActive: true,
      userRole: 'ADMIN', email: 'programtiagodev@gmail.com', contactNumber: '912568714', updatedAt: ''
    }
  ];

  constructor(){
  }

  ngOnInit(): void {
    
  }

}
