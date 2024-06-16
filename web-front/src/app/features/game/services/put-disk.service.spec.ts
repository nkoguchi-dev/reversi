import { TestBed } from '@angular/core/testing';

import { PutDiskService } from './put-disk.service';

describe('PutDiskService', () => {
  let service: PutDiskService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PutDiskService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
