import React from 'react';
import './MainScreen.css';
import StartButton from './StartButton';

function MainScreen() {
    const handleStartGame = () => {
        console.log('ゲームが開始されました！');
        // ゲームの初期化ロジックなどをここに追加
    };

    return (
        <div className="main-screen">
            <h2>Reversi</h2>
            <p>
                技術の学習のために作成しているリバーシゲームです。
                詳細については、<a href="https://github.com/nkoguchi-dev/reversi" target="_blank">GitHubのリポジトリ</a>をご覧ください。
            </p>
            <br/>
            <StartButton onClick={handleStartGame} />
        </div>
    );
}

export default MainScreen;