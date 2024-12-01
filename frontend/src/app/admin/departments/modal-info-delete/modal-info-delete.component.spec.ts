import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalInfoDeleteComponent } from './modal-info-delete.component';

describe('ModalInfoDeleteComponent', () => {
  let component: ModalInfoDeleteComponent;
  let fixture: ComponentFixture<ModalInfoDeleteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ModalInfoDeleteComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModalInfoDeleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
