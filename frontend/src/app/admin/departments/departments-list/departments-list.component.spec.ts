import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DepartmentsListComponent } from './departments-list.component';

describe('DepartmentsListComponent', () => {
  let component: DepartmentsListComponent;
  let fixture: ComponentFixture<DepartmentsListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DepartmentsListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DepartmentsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
