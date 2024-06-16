
export class Disk {
  diskType: DiskType;

  constructor(diskType: DiskType) {
    this.diskType = diskType;
  }
}

export enum DiskType {
  LIGHT = 'LIGHT',
  DARK = 'DARK',
}

function isDiskType(value: 'LIGHT' | 'DARK' | null): value is DiskType {
  return Object.values(DiskType).includes(value as DiskType);
}

export function createDiskType(value: 'LIGHT' | 'DARK' | null): DiskType {
  if (!isDiskType(value)) {
    throw new Error(`Invalid disk type: ${value}`);
  }
  return value;
}
