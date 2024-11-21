import { Component, Input, OnInit } from '@angular/core';
import { User } from '../../../model/user';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrl: './users-list.component.scss'
})
export class UsersListComponent implements OnInit{

  @Input() users: User[] = []

  displayedColumns: String[] = ['firstName', 'lastName', 'workNumber', 'department', 'registryDate', 'isActive', 
    'userRole',  'email', 'contactNumber', 'updatedAt', 'birthdayDate', 'actions'
  ]

  constructor(private router: Router, private route: ActivatedRoute){}

  ngOnInit(): void {}

  onAdd(){
    this.router.navigate(['new'], {relativeTo: this.route})
  }


}
