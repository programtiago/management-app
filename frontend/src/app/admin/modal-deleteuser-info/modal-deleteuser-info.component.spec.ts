import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalDeleteuserInfoComponent } from './modal-deleteuser-info.component';

describe('ModalDeleteuserInfoComponent', () => {
  let component: ModalDeleteuserInfoComponent;
  let fixture: ComponentFixture<ModalDeleteuserInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ModalDeleteuserInfoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModalDeleteuserInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
