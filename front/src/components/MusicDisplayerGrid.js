import React from "react";
import MusicDisplayerCard from './MusicDisplayerCard';

function MusicDisplayerGrid({onSongSelect}) {
    const musicData = [
        {title: 'Goosebumps', artist: 'Travis Scott', logoUrl: 'https://m.media-amazon.com/images/I/81nFF-rXdRL._UF1000,1000_QL80_.jpg'},
        {title: 'SICKO MODE', artist: 'Travis Scott', logoUrl: 'https://m.media-amazon.com/images/I/81nFF-rXdRL._UF1000,1000_QL80_.jpg'},
        {title: 'NC17', artist: 'Travis Scott', logoUrl: 'https://m.media-amazon.com/images/I/81nFF-rXdRL._UF1000,1000_QL80_.jpg'},
        {title: 'Tunnel Vision', artist: 'Kodak Black', logoUrl: 'https://upload.wikimedia.org/wikipedia/en/6/62/Tunnel_Vision_by_Kodak_Black.jpg'}, 
        {title: '100k on a Coupe', artist: 'Pop Smoke', logoUrl: 'https://i.ytimg.com/vi/UN3x0wDElY4/sddefault.jpg'},
        {title: '100k on a Coupe', artist: 'Pop Smoke', logoUrl: 'https://i.ytimg.com/vi/UN3x0wDElY4/sddefault.jpg'},
        {title: '100k on a Coupe', artist: 'Pop Smoke', logoUrl: 'https://i.ytimg.com/vi/UN3x0wDElY4/sddefault.jpg'},
        {title: '100k on a Coupe', artist: 'Pop Smoke', logoUrl: 'https://i.ytimg.com/vi/UN3x0wDElY4/sddefault.jpg'},
    ]
    const styles = {
        musicGrid: {
            marginTop: '0.8em',
            marginBottom: 0, 
            maxHeight: '100%', 
        }
    }
  return (
    <div style={styles.musicGrid}>
      {musicData.slice(0, 5).map((music, index) => (
        <MusicDisplayerCard
          key={index}
          musicTitle={music.title}
          musicArtist={music.artist}
          musicLogoUrl={music.logoUrl}
          onPress={() => onSongSelect(music)}
        />
      ))}
    </div>
  );
}

export default MusicDisplayerGrid;