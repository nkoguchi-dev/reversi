export type Brand<T, U> = T & { __brand: U };

type PrimitiveTypes = "string" | "number" | "boolean" | "symbol";

interface TypeMap {
  string: string;
  number: number;
  boolean: boolean;
  symbol: symbol;
}

export function asBrand<T extends PrimitiveTypes, U extends string>(
  value: TypeMap[T],
  type: T,
  brand: U
): Brand<TypeMap[T], U> {
  return value as Brand<TypeMap[T], U>;
}
