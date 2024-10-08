import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisterationFormComponent } from './registeration-form.component';

describe('RegisterationFormComponent', () => {
  let component: RegisterationFormComponent;
  let fixture: ComponentFixture<RegisterationFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RegisterationFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RegisterationFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
