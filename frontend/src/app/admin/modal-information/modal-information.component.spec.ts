import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalInformationComponent } from './modal-information.component';

describe('ModalInformationComponent', () => {
  let component: ModalInformationComponent;
  let fixture: ComponentFixture<ModalInformationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ModalInformationComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModalInformationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
