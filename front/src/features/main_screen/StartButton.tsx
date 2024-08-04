import React from 'react';
import './StartButton.css';

interface StartButtonProps {
    onClick: () => void;
}

const StartButton: React.FC<StartButtonProps> = ({ onClick }) => {
    return (
        <button onClick={onClick} className='start-button'>
            ゲーム開始
        </button>
    );
};

export default StartButton;
