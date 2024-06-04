package org.koppepan.reversi.webapi.domain.board

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.koppepan.reversi.webapi.domain.shared.CustomIllegalArgumentException

class SquareLineTest {
    @Nested
    @DisplayName("SquareLineHorizontalのテスト")
    inner class SquareLineHorizontalTest {
        @Test
        @DisplayName("SquareLineHorizontalのインスタンスを生成できること")
        fun testSquareLineHorizontal() {
            val horizontalPositions = listOf(
                HorizontalPosition.A,
                HorizontalPosition.B,
                HorizontalPosition.C,
                HorizontalPosition.D,
                HorizontalPosition.E,
                HorizontalPosition.F,
                HorizontalPosition.G,
                HorizontalPosition.H,
            )
            val squares = horizontalPositions.map { Square.create(SquarePosition(it, VerticalPosition.ONE), null) }
            assertDoesNotThrow {
                SquareLine.SquareLineHorizontal.create(squares)
            }
        }

        @Test
        @DisplayName("座標を指定してSquareLineHorizontalのインスタンスを生成できること")
        fun testSquareLineHorizontalFromPosition() {
            val diskMap = DiskMap.of(
                SquarePosition(HorizontalPosition.A, VerticalPosition.TWO) to Disk(DiskType.Light),
                SquarePosition(HorizontalPosition.C, VerticalPosition.TWO) to Disk(DiskType.Light),
                SquarePosition(HorizontalPosition.E, VerticalPosition.TWO) to Disk(DiskType.Dark),
                SquarePosition(HorizontalPosition.G, VerticalPosition.TWO) to Disk(DiskType.Dark),
            )
            val square = assertDoesNotThrow<SquareLine.SquareLineHorizontal> {
                SquareLine.SquareLineHorizontal.createFromPosition(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.TWO),
                    diskMap,
                )
            }
            assertAll(
                { assertEquals(VerticalPosition.TWO, square.squares[0].position.y) },
                { assertEquals(VerticalPosition.TWO, square.squares[1].position.y) },
                { assertEquals(VerticalPosition.TWO, square.squares[2].position.y) },
                { assertEquals(VerticalPosition.TWO, square.squares[3].position.y) },
                { assertEquals(VerticalPosition.TWO, square.squares[4].position.y) },
                { assertEquals(VerticalPosition.TWO, square.squares[5].position.y) },
                { assertEquals(VerticalPosition.TWO, square.squares[6].position.y) },
                { assertEquals(VerticalPosition.TWO, square.squares[7].position.y) },
                { assertEquals(DiskType.Light, square.squares[0].disk?.type) },
                { assertEquals(null, square.squares[1].disk?.type) },
                { assertEquals(DiskType.Light, square.squares[2].disk?.type) },
                { assertEquals(null, square.squares[3].disk?.type) },
                { assertEquals(DiskType.Dark, square.squares[4].disk?.type) },
                { assertEquals(null, square.squares[5].disk?.type) },
                { assertEquals(DiskType.Dark, square.squares[6].disk?.type) },
                { assertEquals(null, square.squares[7].disk?.type) },
            )
        }

        @Test
        @DisplayName("SquareLineHorizontalのSquare数が不正の場合例外が発生すること")
        fun testSquareLineHorizontalSize() {
            val horizontalPositions = listOf(
                HorizontalPosition.A,
                HorizontalPosition.B,
                HorizontalPosition.C,
                HorizontalPosition.D,
                HorizontalPosition.E,
                HorizontalPosition.F,
                HorizontalPosition.G,
            )
            val squares = horizontalPositions.map { Square.create(SquarePosition(it, VerticalPosition.ONE), null) }
            val exception = assertThrows<CustomIllegalArgumentException> {
                SquareLine.SquareLineHorizontal.create(squares)
            }
            assertAll(
                { assertEquals("SquareLineHorizontalは8個の要素を持つ必要があります", exception.message) },
                { assertEquals("", exception.description) },
            )
        }

        @Test
        @DisplayName("SquareLineHorizontalに設定するSquareに異なるy座標ある場合例外が発生すること")
        fun testSquareLineHorizontalY() {
            val squares = listOf(
                Square.create(SquarePosition(HorizontalPosition.A, VerticalPosition.ONE), null),
                Square.create(SquarePosition(HorizontalPosition.B, VerticalPosition.TWO), null),
                Square.create(SquarePosition(HorizontalPosition.C, VerticalPosition.ONE), null),
                Square.create(SquarePosition(HorizontalPosition.D, VerticalPosition.ONE), null),
                Square.create(SquarePosition(HorizontalPosition.E, VerticalPosition.ONE), null),
                Square.create(SquarePosition(HorizontalPosition.F, VerticalPosition.ONE), null),
                Square.create(SquarePosition(HorizontalPosition.G, VerticalPosition.ONE), null),
                Square.create(SquarePosition(HorizontalPosition.H, VerticalPosition.ONE), null),
            )
            val exception = assertThrows<CustomIllegalArgumentException> {
                SquareLine.SquareLineHorizontal.create(squares)
            }
            assertAll(
                {
                    assertEquals(
                        "SquareLineHorizontalは全てのSquareが同じy座標を持つ必要があります",
                        exception.message
                    )
                },
                { assertEquals("", exception.description) },
            )
        }

        @Nested
        @DisplayName("裏返す事が可能な相手のディスクを返却できること")
        inner class GetReversibleDisks {
            @Test
            @DisplayName("プラス方向のディスクが裏返る場合に正常に動作すること")
            fun testGetReversibleDisksPlus() {
                val diskMap = DiskMap.of(
                    SquarePosition(HorizontalPosition.B, VerticalPosition.ONE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.C, VerticalPosition.ONE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.D, VerticalPosition.ONE) to Disk(DiskType.Dark),
                )
                val line = SquareLine.SquareLineHorizontal.createFromPosition(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.ONE),
                    diskMap,
                )
                val actual = line.getReversibleDisks(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.ONE),
                    Disk(DiskType.Dark),
                )

                val expected = mapOf(
                    SquarePosition(HorizontalPosition.B, VerticalPosition.ONE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.C, VerticalPosition.ONE) to Disk(DiskType.Light),
                )
                assertEquals(expected, actual)
            }

            @Test
            @DisplayName("プラス方向のディスクが裏返る場合に余分なディスクを除外して正常に動作すること")
            fun testGetReversibleDisksPlus2() {
                val diskMap = DiskMap.of(
                    SquarePosition(HorizontalPosition.B, VerticalPosition.ONE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.C, VerticalPosition.ONE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.D, VerticalPosition.ONE) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.E, VerticalPosition.ONE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.F, VerticalPosition.ONE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.G, VerticalPosition.ONE) to Disk(DiskType.Dark),
                )
                val line = SquareLine.SquareLineHorizontal.createFromPosition(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.ONE),
                    diskMap,
                )
                val actual = line.getReversibleDisks(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.ONE),
                    Disk(DiskType.Dark),
                )

                val expected = mapOf(
                    SquarePosition(HorizontalPosition.B, VerticalPosition.ONE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.C, VerticalPosition.ONE) to Disk(DiskType.Light),
                )
                assertEquals(expected, actual)
            }

            @Test
            @DisplayName("マイナス方向のディスクが裏返る場合に正常に動作すること")
            fun testGetReversibleDisksMinus() {
                val diskMap = DiskMap.of(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.ONE) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.B, VerticalPosition.ONE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.C, VerticalPosition.ONE) to Disk(DiskType.Light),
                )
                val line = SquareLine.SquareLineHorizontal.createFromPosition(
                    SquarePosition(HorizontalPosition.D, VerticalPosition.ONE),
                    diskMap,
                )
                val actual = line.getReversibleDisks(
                    SquarePosition(HorizontalPosition.D, VerticalPosition.ONE),
                    Disk(DiskType.Dark),
                )

                val expected = mapOf(
                    SquarePosition(HorizontalPosition.B, VerticalPosition.ONE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.C, VerticalPosition.ONE) to Disk(DiskType.Light),
                )
                assertEquals(expected, actual)
            }

            @Test
            @DisplayName("マイナス方向のディスクが裏返る場合に余分なディスクを除外して正常に動作すること")
            fun testGetReversibleDisksMinus2() {
                val diskMap = DiskMap.of(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.ONE) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.B, VerticalPosition.ONE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.C, VerticalPosition.ONE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.D, VerticalPosition.ONE) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.E, VerticalPosition.ONE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.F, VerticalPosition.ONE) to Disk(DiskType.Light),
                )
                val line = SquareLine.SquareLineHorizontal.createFromPosition(
                    SquarePosition(HorizontalPosition.G, VerticalPosition.ONE),
                    diskMap,
                )
                val actual = line.getReversibleDisks(
                    SquarePosition(HorizontalPosition.G, VerticalPosition.ONE),
                    Disk(DiskType.Dark),
                )

                val expected = mapOf(
                    SquarePosition(HorizontalPosition.E, VerticalPosition.ONE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.F, VerticalPosition.ONE) to Disk(DiskType.Light),
                )
                assertEquals(expected, actual)
            }

            @Test
            @DisplayName("挟んだマスに相手のディスク以外に空欄がある場合に正常に動作すること")
            fun testGetReversibleDisksEmpty() {
                val diskMap = DiskMap.of(
                    SquarePosition(HorizontalPosition.C, VerticalPosition.ONE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.D, VerticalPosition.ONE) to Disk(DiskType.Dark),
                )
                val line = SquareLine.SquareLineHorizontal.createFromPosition(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.ONE),
                    diskMap,
                )
                val actual = line.getReversibleDisks(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.ONE),
                    Disk(DiskType.Dark),
                )

                val expected = emptyMap<SquarePosition, Disk?>()
                assertEquals(expected, actual)
            }

            @Test
            @DisplayName("双方向にあるディスクが裏返る場合に正常に動作すること")
            fun testGetReversibleDisksPlusAndMinus() {
                val diskMap = DiskMap.of(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.ONE) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.B, VerticalPosition.ONE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.C, VerticalPosition.ONE) to Disk(DiskType.Light),
                    //SquarePosition(HorizontalPosition.D, VerticalPosition.ONE) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.E, VerticalPosition.ONE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.F, VerticalPosition.ONE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.G, VerticalPosition.ONE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.H, VerticalPosition.ONE) to Disk(DiskType.Dark),
                )
                val line = SquareLine.SquareLineHorizontal.createFromPosition(
                    SquarePosition(HorizontalPosition.D, VerticalPosition.ONE),
                    diskMap,
                )
                val actual = line.getReversibleDisks(
                    SquarePosition(HorizontalPosition.D, VerticalPosition.ONE),
                    Disk(DiskType.Dark),
                )

                val expected = mapOf(
                    SquarePosition(HorizontalPosition.B, VerticalPosition.ONE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.C, VerticalPosition.ONE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.E, VerticalPosition.ONE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.F, VerticalPosition.ONE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.G, VerticalPosition.ONE) to Disk(DiskType.Light),
                )
                assertEquals(expected, actual)
            }

            @Test
            @DisplayName("双方向にあるディスクが裏返る場合に余分なディスクを除外して正常に動作すること")
            fun testGetReversibleDisksPlusAndMinus2() {
                val diskMap = DiskMap.of(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.ONE) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.B, VerticalPosition.ONE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.C, VerticalPosition.ONE) to Disk(DiskType.Light),
                    //SquarePosition(HorizontalPosition.D, VerticalPosition.ONE) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.E, VerticalPosition.ONE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.F, VerticalPosition.ONE) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.G, VerticalPosition.ONE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.H, VerticalPosition.ONE) to Disk(DiskType.Dark),
                )
                val line = SquareLine.SquareLineHorizontal.createFromPosition(
                    SquarePosition(HorizontalPosition.D, VerticalPosition.ONE),
                    diskMap,
                )
                val actual = line.getReversibleDisks(
                    SquarePosition(HorizontalPosition.D, VerticalPosition.ONE),
                    Disk(DiskType.Dark),
                )

                val expected = mapOf(
                    SquarePosition(HorizontalPosition.B, VerticalPosition.ONE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.C, VerticalPosition.ONE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.E, VerticalPosition.ONE) to Disk(DiskType.Light),
                )
                assertEquals(expected, actual)
            }

            @Test
            @DisplayName("双方向にあるディスクが裏返る場合に空白のマスがある場合でも正常に動作すること")
            fun testGetReversibleDisksPlusAndMinus3() {
                val diskMap = DiskMap.of(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.ONE) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.B, VerticalPosition.ONE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.C, VerticalPosition.ONE) to null,
                    //SquarePosition(HorizontalPosition.D, VerticalPosition.ONE) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.E, VerticalPosition.ONE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.F, VerticalPosition.ONE) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.G, VerticalPosition.ONE) to null,
                    SquarePosition(HorizontalPosition.H, VerticalPosition.ONE) to Disk(DiskType.Dark),
                )
                val line = SquareLine.SquareLineHorizontal.createFromPosition(
                    SquarePosition(HorizontalPosition.D, VerticalPosition.ONE),
                    diskMap,
                )
                val actual = line.getReversibleDisks(
                    SquarePosition(HorizontalPosition.D, VerticalPosition.ONE),
                    Disk(DiskType.Dark),
                )

                val expected = mapOf(
                    SquarePosition(HorizontalPosition.E, VerticalPosition.ONE) to Disk(DiskType.Light),
                )
                assertEquals(expected, actual)
            }
        }
    }

    @Nested
    @DisplayName("SquareLineVerticalのテスト")
    inner class SquareLineVerticalTest {
        @Test
        @DisplayName("SquareLineVerticalのインスタンスを生成できること")
        fun testSquareLineVertical() {
            val verticalPositions = listOf(
                VerticalPosition.ONE,
                VerticalPosition.TWO,
                VerticalPosition.THREE,
                VerticalPosition.FOUR,
                VerticalPosition.FIVE,
                VerticalPosition.SIX,
                VerticalPosition.SEVEN,
                VerticalPosition.EIGHT,
            )
            val squares = verticalPositions.map { Square.create(SquarePosition(HorizontalPosition.A, it), null) }
            assertDoesNotThrow {
                SquareLine.SquareLineVertical.create(squares)
            }
        }

        @Test
        @DisplayName("座標を指定してSquareLineVerticalのインスタンスを生成できること")
        fun testSquareLineVerticalFromPosition() {
            val diskMap = DiskMap.of(
                SquarePosition(HorizontalPosition.B, VerticalPosition.ONE) to Disk(DiskType.Light),
                SquarePosition(HorizontalPosition.B, VerticalPosition.TWO) to Disk(DiskType.Light),
                SquarePosition(HorizontalPosition.B, VerticalPosition.THREE) to Disk(DiskType.Dark),
                SquarePosition(HorizontalPosition.B, VerticalPosition.FOUR) to Disk(DiskType.Dark),
            )
            val square = assertDoesNotThrow<SquareLine.SquareLineVertical> {
                SquareLine.SquareLineVertical.createFromPosition(
                    SquarePosition(HorizontalPosition.B, VerticalPosition.ONE),
                    diskMap,
                )
            }
            assertAll(
                { assertEquals(HorizontalPosition.B, square.squares[0].position.x) },
                { assertEquals(HorizontalPosition.B, square.squares[1].position.x) },
                { assertEquals(HorizontalPosition.B, square.squares[2].position.x) },
                { assertEquals(HorizontalPosition.B, square.squares[3].position.x) },
                { assertEquals(HorizontalPosition.B, square.squares[4].position.x) },
                { assertEquals(HorizontalPosition.B, square.squares[5].position.x) },
                { assertEquals(HorizontalPosition.B, square.squares[6].position.x) },
                { assertEquals(HorizontalPosition.B, square.squares[7].position.x) },
                { assertEquals(DiskType.Light, square.squares[0].disk?.type) },
                { assertEquals(DiskType.Light, square.squares[1].disk?.type) },
                { assertEquals(DiskType.Dark, square.squares[2].disk?.type) },
                { assertEquals(DiskType.Dark, square.squares[3].disk?.type) },
                { assertEquals(null, square.squares[4].disk?.type) },
                { assertEquals(null, square.squares[5].disk?.type) },
                { assertEquals(null, square.squares[6].disk?.type) },
                { assertEquals(null, square.squares[7].disk?.type) },
            )
        }


        @Test
        @DisplayName("SquareLineVerticalのSquare数が不正の場合例外が発生すること")
        fun testSquareLineVerticalSize() {
            val verticalPositions = listOf(
                VerticalPosition.ONE,
                VerticalPosition.TWO,
                VerticalPosition.THREE,
                VerticalPosition.FOUR,
                VerticalPosition.FIVE,
                VerticalPosition.SIX,
                VerticalPosition.SEVEN,
            )
            val squares = verticalPositions.map { Square.create(SquarePosition(HorizontalPosition.A, it), null) }
            val exception = assertThrows<CustomIllegalArgumentException> {
                SquareLine.SquareLineVertical.create(squares)
            }
            assertAll(
                { assertEquals("SquareLineVerticalは8個の要素を持つ必要があります", exception.message) },
                { assertEquals("", exception.description) },
            )
        }

        @Test
        @DisplayName("SquareLineVerticalに設定するSquareに異なるx座標ある場合例外が発生すること")
        fun testSquareLineVerticalX() {
            val squares = listOf(
                Square.create(SquarePosition(HorizontalPosition.A, VerticalPosition.ONE), null),
                Square.create(SquarePosition(HorizontalPosition.A, VerticalPosition.TWO), null),
                Square.create(SquarePosition(HorizontalPosition.B, VerticalPosition.THREE), null),
                Square.create(SquarePosition(HorizontalPosition.A, VerticalPosition.FOUR), null),
                Square.create(SquarePosition(HorizontalPosition.A, VerticalPosition.FIVE), null),
                Square.create(SquarePosition(HorizontalPosition.A, VerticalPosition.SIX), null),
                Square.create(SquarePosition(HorizontalPosition.A, VerticalPosition.SEVEN), null),
                Square.create(SquarePosition(HorizontalPosition.A, VerticalPosition.EIGHT), null),
            )
            val exception = assertThrows<CustomIllegalArgumentException> {
                SquareLine.SquareLineVertical.create(squares)
            }
            assertAll(
                {
                    assertEquals(
                        "SquareLineVerticalは全てのSquareが同じx座標を持つ必要があります",
                        exception.message
                    )
                },
                { assertEquals("", exception.description) },
            )
        }

        @Nested
        @DisplayName("裏返す事が可能な相手のディスクを返却できること")
        inner class GetReversibleDisks {
            @Test
            @DisplayName("プラス方向のディスクが裏返る場合に正常に動作すること")
            fun testGetReversibleDisksPlus() {
                val diskMap = DiskMap.of(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.TWO) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.A, VerticalPosition.THREE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.A, VerticalPosition.FOUR) to Disk(DiskType.Dark),
                )
                val line = SquareLine.SquareLineVertical.createFromPosition(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.ONE),
                    diskMap,
                )
                val actual = line.getReversibleDisks(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.ONE),
                    Disk(DiskType.Dark),
                )

                val expected = mapOf(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.TWO) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.A, VerticalPosition.THREE) to Disk(DiskType.Light),
                )
                assertEquals(expected, actual)
            }

            @Test
            @DisplayName("プラス方向のディスクが裏返る場合に余分なディスクを除外して正常に動作すること")
            fun testGetReversibleDisksPlus2() {
                val diskMap = DiskMap.of(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.TWO) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.A, VerticalPosition.THREE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.A, VerticalPosition.FOUR) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.A, VerticalPosition.FIVE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.A, VerticalPosition.SIX) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.A, VerticalPosition.SEVEN) to Disk(DiskType.Dark),
                )
                val line = SquareLine.SquareLineVertical.createFromPosition(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.ONE),
                    diskMap,
                )
                val actual = line.getReversibleDisks(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.ONE),
                    Disk(DiskType.Dark),
                )

                val expected = mapOf(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.TWO) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.A, VerticalPosition.THREE) to Disk(DiskType.Light),
                )
                assertEquals(expected, actual)
            }

            @Test
            @DisplayName("マイナス方向のディスクが裏返る場合に正常に動作すること")
            fun testGetReversibleDisksMinus() {
                val diskMap = DiskMap.of(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.ONE) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.A, VerticalPosition.TWO) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.A, VerticalPosition.THREE) to Disk(DiskType.Light),
                )
                val line = SquareLine.SquareLineVertical.createFromPosition(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.ONE),
                    diskMap,
                )
                val actual = line.getReversibleDisks(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.FOUR),
                    Disk(DiskType.Dark),
                )

                val expected = mapOf(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.TWO) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.A, VerticalPosition.THREE) to Disk(DiskType.Light),
                )
                assertEquals(expected, actual)
            }

            @Test
            @DisplayName("マイナス方向のディスクが裏返る場合に余分なディスクを除外して正常に動作すること")
            fun testGetReversibleDisksMinus2() {
                val diskMap = DiskMap.of(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.ONE) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.A, VerticalPosition.TWO) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.A, VerticalPosition.THREE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.A, VerticalPosition.FOUR) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.A, VerticalPosition.FIVE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.A, VerticalPosition.SIX) to Disk(DiskType.Light),
                )
                val line = SquareLine.SquareLineVertical.createFromPosition(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.SEVEN),
                    diskMap,
                )
                val actual = line.getReversibleDisks(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.SEVEN),
                    Disk(DiskType.Dark),
                )

                val expected = mapOf(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.FIVE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.A, VerticalPosition.SIX) to Disk(DiskType.Light),
                )
                assertEquals(expected, actual)
            }

            @Test
            @DisplayName("挟んだマスに相手のディスク以外に空欄がある場合に正常に動作すること")
            fun testGetReversibleDisksEmpty() {
                val diskMap = DiskMap.of(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.THREE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.A, VerticalPosition.FOUR) to Disk(DiskType.Dark),
                )
                val line = SquareLine.SquareLineVertical.createFromPosition(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.ONE),
                    diskMap,
                )
                val actual = line.getReversibleDisks(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.ONE),
                    Disk(DiskType.Dark),
                )

                val expected = emptyMap<SquarePosition, Disk?>()
                assertEquals(expected, actual)
            }

            @Test
            @DisplayName("双方向にあるディスクが裏返る場合に正常に動作すること")
            fun testGetReversibleDisksPlusAndMinus() {
                val diskMap = DiskMap.of(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.ONE) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.A, VerticalPosition.TWO) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.A, VerticalPosition.THREE) to Disk(DiskType.Light),
                    //SquarePosition(HorizontalPosition.A, VerticalPosition.FOUR) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.A, VerticalPosition.FIVE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.A, VerticalPosition.SIX) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.A, VerticalPosition.SEVEN) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.A, VerticalPosition.EIGHT) to Disk(DiskType.Dark),
                )
                val line = SquareLine.SquareLineVertical.createFromPosition(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.FOUR),
                    diskMap,
                )
                val actual = line.getReversibleDisks(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.FOUR),
                    Disk(DiskType.Dark),
                )

                val expected = mapOf(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.TWO) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.A, VerticalPosition.THREE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.A, VerticalPosition.FIVE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.A, VerticalPosition.SIX) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.A, VerticalPosition.SEVEN) to Disk(DiskType.Light),
                )
                assertEquals(expected, actual)
            }

            @Test
            @DisplayName("双方向にあるディスクが裏返る場合に余分なディスクを除外して正常に動作すること")
            fun testGetReversibleDisksPlusAndMinus2() {
                val diskMap = DiskMap.of(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.ONE) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.A, VerticalPosition.TWO) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.A, VerticalPosition.THREE) to Disk(DiskType.Light),
                    //SquarePosition(HorizontalPosition.A, VerticalPosition.FOUR) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.A, VerticalPosition.FIVE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.A, VerticalPosition.SIX) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.A, VerticalPosition.SEVEN) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.A, VerticalPosition.EIGHT) to Disk(DiskType.Dark),
                )
                val line = SquareLine.SquareLineVertical.createFromPosition(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.FOUR),
                    diskMap,
                )
                val actual = line.getReversibleDisks(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.FOUR),
                    Disk(DiskType.Dark),
                )

                val expected = mapOf(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.TWO) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.A, VerticalPosition.THREE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.A, VerticalPosition.FIVE) to Disk(DiskType.Light),
                )
                assertEquals(expected, actual)
            }

            @Test
            @DisplayName("双方向にあるディスクが裏返る場合に空白のマスがある場合でも正常に動作すること")
            fun testGetReversibleDisksPlusAndMinus3() {
                val diskMap = DiskMap.of(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.ONE) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.A, VerticalPosition.TWO) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.A, VerticalPosition.THREE) to null,
                    //SquarePosition(HorizontalPosition.A, VerticalPosition.FOUR) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.A, VerticalPosition.FIVE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.A, VerticalPosition.SIX) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.A, VerticalPosition.SEVEN) to null,
                    SquarePosition(HorizontalPosition.A, VerticalPosition.EIGHT) to Disk(DiskType.Dark),
                )
                val line = SquareLine.SquareLineVertical.createFromPosition(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.FOUR),
                    diskMap,
                )
                val actual = line.getReversibleDisks(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.FOUR),
                    Disk(DiskType.Dark),
                )

                val expected = mapOf(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.FIVE) to Disk(DiskType.Light),
                )
                assertEquals(expected, actual)
            }
        }
    }

    @Nested
    @DisplayName("SquareLineDiagonalのテスト")
    inner class SquareLineDiagonal {
        @Test
        @DisplayName("SquareLineDiagonalのインスタンスを生成できること")
        fun testSquareLineDiagonal() {
            val squares = listOf(
                Square.create(SquarePosition(HorizontalPosition.A, VerticalPosition.ONE), null),
                Square.create(SquarePosition(HorizontalPosition.B, VerticalPosition.TWO), null),
                Square.create(SquarePosition(HorizontalPosition.C, VerticalPosition.THREE), null),
                Square.create(SquarePosition(HorizontalPosition.D, VerticalPosition.FOUR), null),
                Square.create(SquarePosition(HorizontalPosition.E, VerticalPosition.FIVE), null),
                Square.create(SquarePosition(HorizontalPosition.F, VerticalPosition.SIX), null),
                Square.create(SquarePosition(HorizontalPosition.G, VerticalPosition.SEVEN), null),
                Square.create(SquarePosition(HorizontalPosition.H, VerticalPosition.EIGHT), null),
            )
            assertDoesNotThrow {
                SquareLine.SquareLineDiagonal.create(squares)
            }
        }

        @Test
        @DisplayName("座標を指定してSquareLineDiagonalのインスタンスを生成できること")
        fun testSquareLineDiagonalFromPosition() {
            // TODO: このへんテスト項目が足りてない
            val diskMap = DiskMap.of()
            val squares = assertDoesNotThrow<List<SquareLine.SquareLineDiagonal>> {
                SquareLine.SquareLineDiagonal.createFromPosition(
                    SquarePosition(HorizontalPosition.D, VerticalPosition.FOUR),
                    diskMap,
                )
            }
            assertAll(
                {
                    assertEquals(
                        SquarePosition(HorizontalPosition.A, VerticalPosition.ONE),
                        squares[0].squares[0].position
                    )
                },
            )
        }

        @Test
        @DisplayName("SquareLineDiagonalの配列順序が逆順でもインスタンスを生成できること")
        fun testSquareLineDiagonalReverse() {
            val squares = listOf(
                Square.create(SquarePosition(HorizontalPosition.H, VerticalPosition.EIGHT), null),
                Square.create(SquarePosition(HorizontalPosition.G, VerticalPosition.SEVEN), null),
                Square.create(SquarePosition(HorizontalPosition.F, VerticalPosition.SIX), null),
                Square.create(SquarePosition(HorizontalPosition.E, VerticalPosition.FIVE), null),
                Square.create(SquarePosition(HorizontalPosition.D, VerticalPosition.FOUR), null),
                Square.create(SquarePosition(HorizontalPosition.C, VerticalPosition.THREE), null),
                Square.create(SquarePosition(HorizontalPosition.B, VerticalPosition.TWO), null),
                Square.create(SquarePosition(HorizontalPosition.A, VerticalPosition.ONE), null),
            )
            assertDoesNotThrow {
                SquareLine.SquareLineDiagonal.create(squares)
            }
        }

        @Test
        @DisplayName("SquareLineDiagonalのSquareが3個のインスタンスを生成できること")
        fun testSquareLineDiagonalThree() {
            val squares = listOf(
                Square.create(SquarePosition(HorizontalPosition.A, VerticalPosition.THREE), null),
                Square.create(SquarePosition(HorizontalPosition.B, VerticalPosition.TWO), null),
                Square.create(SquarePosition(HorizontalPosition.C, VerticalPosition.ONE), null),
            )
            assertDoesNotThrow {
                SquareLine.SquareLineDiagonal.create(squares)
            }
        }

        @Test
        @DisplayName("SquareLineDiagonalのSquareが3個のインスタンスを生成できること")
        fun testSquareLineDiagonalThree2() {
            val squares = listOf(
                Square.create(SquarePosition(HorizontalPosition.F, VerticalPosition.ONE), null),
                Square.create(SquarePosition(HorizontalPosition.G, VerticalPosition.TWO), null),
                Square.create(SquarePosition(HorizontalPosition.H, VerticalPosition.THREE), null),
            )
            assertDoesNotThrow {
                SquareLine.SquareLineDiagonal.create(squares)
            }
        }

        @Test
        @DisplayName("SquareLineDiagonalの盤目の端から端に到達していないインスタンスを生成した場合例外が発生すること")
        fun testSquareLineDiagonalNotReach() {
            val squares = listOf(
                Square.create(SquarePosition(HorizontalPosition.A, VerticalPosition.ONE), null),
                Square.create(SquarePosition(HorizontalPosition.B, VerticalPosition.TWO), null),
                Square.create(SquarePosition(HorizontalPosition.C, VerticalPosition.THREE), null),
            )
            val exception = assertThrows<CustomIllegalArgumentException> {
                SquareLine.SquareLineDiagonal.create(squares)
            }
            assertAll(
                {
                    assertEquals(
                        "SquareLineDiagonalはBoardの端から端に並ぶ要素を持つ必要があります",
                        exception.message
                    )
                },
                { assertEquals("", exception.description) },
            )
        }

        @Test
        @DisplayName("SquareLineDiagonalのSquareが空の場合例外が発生すること")
        fun testSquareLineDiagonalSize() {
            val exception = assertThrows<CustomIllegalArgumentException> {
                SquareLine.SquareLineDiagonal.create(listOf())
            }
            assertAll(
                { assertEquals("SquareLineDiagonalは3つ以上の要素を持つ必要があります", exception.message) },
                { assertEquals("", exception.description) },
            )
        }

        @Test
        @DisplayName("SquareLineDiagonalのSquareのx座標とy座標が不正の場合例外が発生すること")
        fun testSquareLineDiagonalXY() {
            val squares = listOf(
                Square.create(SquarePosition(HorizontalPosition.A, VerticalPosition.ONE), null),
                Square.create(SquarePosition(HorizontalPosition.B, VerticalPosition.TWO), null),
                Square.create(SquarePosition(HorizontalPosition.C, VerticalPosition.THREE), null),
                Square.create(SquarePosition(HorizontalPosition.D, VerticalPosition.FOUR), null),
                Square.create(SquarePosition(HorizontalPosition.E, VerticalPosition.FIVE), null),
                Square.create(SquarePosition(HorizontalPosition.F, VerticalPosition.SIX), null),
                Square.create(SquarePosition(HorizontalPosition.G, VerticalPosition.SEVEN), null),
                Square.create(SquarePosition(HorizontalPosition.H, VerticalPosition.SEVEN), null),
            )
            val exception = assertThrows<CustomIllegalArgumentException> {
                SquareLine.SquareLineDiagonal.create(squares)
            }
            assertAll(
                { assertEquals("SquareLineDiagonalは斜めの要素を持つ必要があります", exception.message) },
                { assertEquals("", exception.description) },
            )
        }

        @Nested
        @DisplayName("裏返す事が可能な相手のディスクを返却できること")
        inner class GetReversibleDisks {
            @Test
            @DisplayName("プラス方向のディスクが裏返る場合に正常に動作すること")
            fun testGetReversibleDisksPlus() {
                val diskMap = DiskMap.of(
                    SquarePosition(HorizontalPosition.B, VerticalPosition.TWO) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.C, VerticalPosition.THREE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.D, VerticalPosition.FOUR) to Disk(DiskType.Dark),
                )
                val line = SquareLine.SquareLineDiagonal.createFromPosition(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.ONE),
                    diskMap,
                ).first()
                val actual = line.getReversibleDisks(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.ONE),
                    Disk(DiskType.Dark),
                )

                val expected = mapOf(
                    SquarePosition(HorizontalPosition.B, VerticalPosition.TWO) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.C, VerticalPosition.THREE) to Disk(DiskType.Light),
                )
                assertEquals(expected, actual)
            }

            @Test
            @DisplayName("プラス方向のディスクが裏返る場合に余分なディスクを除外して正常に動作すること")
            fun testGetReversibleDisksPlus2() {
                val diskMap = DiskMap.of(
                    SquarePosition(HorizontalPosition.B, VerticalPosition.TWO) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.C, VerticalPosition.THREE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.D, VerticalPosition.FOUR) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.E, VerticalPosition.FIVE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.F, VerticalPosition.SIX) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.G, VerticalPosition.SEVEN) to Disk(DiskType.Dark),
                )
                val line = SquareLine.SquareLineDiagonal.createFromPosition(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.ONE),
                    diskMap,
                ).first()
                val actual = line.getReversibleDisks(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.ONE),
                    Disk(DiskType.Dark),
                )

                val expected = mapOf(
                    SquarePosition(HorizontalPosition.B, VerticalPosition.TWO) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.C, VerticalPosition.THREE) to Disk(DiskType.Light),
                )
                assertEquals(expected, actual)
            }

            @Test
            @DisplayName("マイナス方向のディスクが裏返る場合に正常に動作すること")
            fun testGetReversibleDisksMinus() {
                val diskMap = DiskMap.of(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.ONE) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.B, VerticalPosition.TWO) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.C, VerticalPosition.THREE) to Disk(DiskType.Light),
                )
                val line = SquareLine.SquareLineDiagonal.createFromPosition(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.ONE),
                    diskMap,
                ).first()
                val actual = line.getReversibleDisks(
                    SquarePosition(HorizontalPosition.D, VerticalPosition.FOUR),
                    Disk(DiskType.Dark),
                )

                val expected = mapOf(
                    SquarePosition(HorizontalPosition.B, VerticalPosition.TWO) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.C, VerticalPosition.THREE) to Disk(DiskType.Light),
                )
                assertEquals(expected, actual)
            }

            @Test
            @DisplayName("マイナス方向のディスクが裏返る場合に余分なディスクを除外して正常に動作すること")
            fun testGetReversibleDisksMinus2() {
                val diskMap = DiskMap.of(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.ONE) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.B, VerticalPosition.TWO) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.C, VerticalPosition.THREE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.D, VerticalPosition.FOUR) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.E, VerticalPosition.FIVE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.F, VerticalPosition.SIX) to Disk(DiskType.Light),
                )
                val line = SquareLine.SquareLineDiagonal.createFromPosition(
                    SquarePosition(HorizontalPosition.G, VerticalPosition.SEVEN),
                    diskMap,
                ).first()
                val actual = line.getReversibleDisks(
                    SquarePosition(HorizontalPosition.G, VerticalPosition.SEVEN),
                    Disk(DiskType.Dark),
                )

                val expected = mapOf(
                    SquarePosition(HorizontalPosition.E, VerticalPosition.FIVE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.F, VerticalPosition.SIX) to Disk(DiskType.Light),
                )
                assertEquals(expected, actual)
            }

            @Test
            @DisplayName("挟んだマスに相手のディスク以外に空欄がある場合に正常に動作すること")
            fun testGetReversibleDisksEmpty() {
                val diskMap = DiskMap.of(
                    SquarePosition(HorizontalPosition.C, VerticalPosition.THREE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.D, VerticalPosition.FOUR) to Disk(DiskType.Dark),
                )
                val line = SquareLine.SquareLineDiagonal.createFromPosition(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.ONE),
                    diskMap,
                ).first()
                val actual = line.getReversibleDisks(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.ONE),
                    Disk(DiskType.Dark),
                )

                val expected = emptyMap<SquarePosition, Disk?>()
                assertEquals(expected, actual)
            }

            @Test
            @DisplayName("双方向にあるディスクが裏返る場合に正常に動作すること")
            fun testGetReversibleDisksPlusAndMinus() {
                val diskMap = DiskMap.of(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.ONE) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.B, VerticalPosition.TWO) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.C, VerticalPosition.THREE) to Disk(DiskType.Light),
                    //SquarePosition(HorizontalPosition.D, VerticalPosition.FOUR) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.E, VerticalPosition.FIVE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.F, VerticalPosition.SIX) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.G, VerticalPosition.SEVEN) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.H, VerticalPosition.EIGHT) to Disk(DiskType.Dark),
                )
                val line = SquareLine.SquareLineDiagonal.createFromPosition(
                    SquarePosition(HorizontalPosition.D, VerticalPosition.FOUR),
                    diskMap,
                ).first()
                val actual = line.getReversibleDisks(
                    SquarePosition(HorizontalPosition.D, VerticalPosition.FOUR),
                    Disk(DiskType.Dark),
                )

                val expected = mapOf(
                    SquarePosition(HorizontalPosition.B, VerticalPosition.TWO) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.C, VerticalPosition.THREE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.E, VerticalPosition.FIVE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.F, VerticalPosition.SIX) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.G, VerticalPosition.SEVEN) to Disk(DiskType.Light),
                )
                assertEquals(expected, actual)
            }

            @Test
            @DisplayName("双方向にあるディスクが裏返る場合に余分なディスクを除外して正常に動作すること")
            fun testGetReversibleDisksPlusAndMinus2() {
                val diskMap = DiskMap.of(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.ONE) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.B, VerticalPosition.TWO) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.C, VerticalPosition.THREE) to Disk(DiskType.Light),
                    //SquarePosition(HorizontalPosition.D, VerticalPosition.FOUR) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.E, VerticalPosition.FIVE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.F, VerticalPosition.SIX) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.G, VerticalPosition.SEVEN) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.H, VerticalPosition.EIGHT) to Disk(DiskType.Dark),
                )
                val line = SquareLine.SquareLineDiagonal.createFromPosition(
                    SquarePosition(HorizontalPosition.D, VerticalPosition.FOUR),
                    diskMap,
                ).first()
                val actual = line.getReversibleDisks(
                    SquarePosition(HorizontalPosition.D, VerticalPosition.FOUR),
                    Disk(DiskType.Dark),
                )

                val expected = mapOf(
                    SquarePosition(HorizontalPosition.B, VerticalPosition.TWO) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.C, VerticalPosition.THREE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.E, VerticalPosition.FIVE) to Disk(DiskType.Light),
                )
                assertEquals(expected, actual)
            }

            @Test
            @DisplayName("双方向にあるディスクが裏返る場合に空白のマスがある場合でも正常に動作すること")
            fun testGetReversibleDisksPlusAndMinus3() {
                val diskMap = DiskMap.of(
                    SquarePosition(HorizontalPosition.A, VerticalPosition.ONE) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.B, VerticalPosition.TWO) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.C, VerticalPosition.THREE) to null,
                    //SquarePosition(HorizontalPosition.D, VerticalPosition.FOUR) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.E, VerticalPosition.FIVE) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.F, VerticalPosition.SIX) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.G, VerticalPosition.SEVEN) to null,
                    SquarePosition(HorizontalPosition.H, VerticalPosition.EIGHT) to Disk(DiskType.Dark),
                )
                val line = SquareLine.SquareLineDiagonal.createFromPosition(
                    SquarePosition(HorizontalPosition.D, VerticalPosition.FOUR),
                    diskMap,
                ).first()
                val actual = line.getReversibleDisks(
                    SquarePosition(HorizontalPosition.D, VerticalPosition.FOUR),
                    Disk(DiskType.Dark),
                )

                val expected = mapOf(
                    SquarePosition(HorizontalPosition.E, VerticalPosition.FIVE) to Disk(DiskType.Light),
                )
                assertEquals(expected, actual)
            }
        }
    }
}