import { TestBed } from '@angular/core/testing';
<<<<<<< HEAD
import { VendorService } from './vendor.service';
=======

import { VendorService } from '../vendor.service';
>>>>>>> origin/main

describe('VendorService', () => {
  let service: VendorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VendorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
