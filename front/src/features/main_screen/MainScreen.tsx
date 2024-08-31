import React, {useState} from 'react';
import './MainScreen.css';
import StartButton from './StartButton';
import {GameStartRequest, GameStartResponse, GameStartService} from "./services/GameStartApiService";

const gameStartService = new GameStartService();

function MainScreen() {
    const [gameData, setGameData] = useState<GameStartResponse | null>(null);
    const [error, setError] = useState<string | null>(null);
    const handleStartGame = async () => {
        const request: GameStartRequest = {
            player1: 'Player 1',
            player2: 'Player 2',
        };
        try {
            const data = await gameStartService.startGame(request);
            setGameData(data);
        } catch (err) {
            setError('ゲームの開始に失敗しました。');
        }
    };

    return (
        <div className="main-screen">
            <h2>Reversi</h2>
            <p>
                技術の学習のために作成しているリバーシゲームです。
                詳細については、<a href="https://github.com/nkoguchi-dev/reversi" target="_blank">GitHubのリポジトリ</a>をご覧ください。
            </p>
            <br/>
            <StartButton onClick={handleStartGame}/>
            {error && <p>{error}</p>}
            {gameData && (
                <div>
                    <h2>ゲームID: {gameData.gameId}</h2>
                    <p>プレイヤー1: {gameData.player1Name}</p>
                    <p>プレイヤー2: {gameData.player2Name}</p>
                    <p>次のプレイヤー: {gameData.nextPlayer}</p>
                </div>
            )}
        </div>
    );
}

export default MainScreen;