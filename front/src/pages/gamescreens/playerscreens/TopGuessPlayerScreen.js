import React, { useState } from "react";
import InputTextBox from "../../../components/InputTextBox";
import GoBackButton from "../../../components/GoBackButton";
import MusicDisplayerGrid from "../../../components/MusicDisplayerGrid";
import PlayerCard from "../../../components/PlayerCard";
import colors from "../../../assets/styles/colors";
import AppButton from "../../../components/AppButton";
import Spacer from "../../../components/Spacer";

// import axios from "axios";
import styles from "../../../assets/styles/styles";

const dummyArtistsData = [
	{
	  artistId: { value: "6412" },
	  artistName: "KYLE",
	  imageUrl: "https://images.genius.com/02f6bb6745dd26a10bf72e200d0a5f43.680x680x1.jpg",
	},
	{
	  artistId: { value: "1985" },
	  artistName: "Frank Ocean",
	  imageUrl: "https://images.genius.com/3a240e0e7829e4d8354b5b63cb87d436.1000x1000x1.png",
	},
	{
	  artistId: { value: "1177" },
	  artistName: "Taylor Swift",
	  imageUrl: "https://images.genius.com/df4a816f593b08bc8a361ad58a848640.1000x1000x1.jpg",
	},
	{
	  artistId: { value: "68" },
	  artistName: "Kid Cudi",
	  imageUrl: "https://images.genius.com/26da54297be506aeebdbff03306223ad.640x640x1.jpg",
	},
	{
	  artistId: { value: "1426407" },
	  artistName: "Lil Tjay",
	  imageUrl: "https://images.genius.com/f523a66b7646deff9dc204d606f31c1c.749x749x1.jpg",
	},
	{
	  artistId: { value: "673238" },
	  artistName: "JoJo Siwa",
	  imageUrl: "https://images.genius.com/ef4904844555f1067c27fda03fb82e5d.400x400x1.jpg",
	},
  ];
  
const TopGuessPlayerScreen = ({roomData, playerData}) => {
  const [artistSearch, setArtistSearch] = useState("");
  const [artistsData, setArtistsData] = useState([]);
  const [selectedArtists, setSelectedArtists] = useState([]);
  const [selectedArtist, setSelectedArtist] = useState(null);
  const [isReadOnly, setIsReadOnly] = useState(false);


  const handleArtistSearchChange = (event) => {
	//setArtistSearch(event.target.value);
	// axios
	//   .get(`${process.env.REACT_APP_BFF_URL}/api/v1/artists/${event.target.value}`)
	//   .then((response) => {
	// 	setArtistsData(response.data);
    //     console.log("artistsData", artistsData);

	//   })
	//   .catch((error) => {
	// 	console.error(error);
	//   });
	// const filteredArtists = dummyArtistsData.filter((artist) =>
	// 	artist.artistName.toLowerCase().includes(event.target.value.toLowerCase())
	//   );
    const searchValue = event.target.value.toLowerCase();
	setArtistSearch(searchValue);
    const filteredArtists = dummyArtistsData.filter(
      (artist) =>
        artist.artistName.toLowerCase().includes(searchValue) &&
        !selectedArtists.some(
          (selectedArtist) => selectedArtist.artistId.value === artist.artistId.value
        )
    );
    setArtistsData(filteredArtists);
  };

  const handleArtistSelect = (artist) => {
    setIsReadOnly(true);
    setSelectedArtist(artist);
	setArtistSearch("");
	setArtistsData((prevArtistsData) =>
		prevArtistsData.filter((a) => a.artistId.value !== artist.artistId.value)
	  );
  };

  const handleCancel = () => {
	setIsReadOnly(false);
	setSelectedArtist(null);
	  };

	  const handleConfirmSelection = () => {
		if (!selectedArtists.some((artist) => artist.artistId.value === selectedArtist.artistId.value)) {
		  setSelectedArtists([...selectedArtists, selectedArtist]);
		  setSelectedArtist(null);
		  setIsReadOnly(false);
		}
    if (selectedArtists.length === 4) {
      setIsReadOnly(true);
      setArtistsData([]);
	  console.log("selectedArtist.length",selectedArtist.length)
    } 
    };
	
  const roundText =
    roomData.currentRound === 1
      ? "1er tour"
      : `${roomData.currentRound}ème tour`;
  return (
	<div style={styles.container}>
		<GoBackButton
        style={styles.backButton}
        title="Quitter la partie"
        bgColor="black"
        color="white"
      />
      <h4 style={styles.h4}>{roundText}</h4>
	  <PlayerCard
        username={playerData.username}
        avatar={playerData.profilePictureUrl}
      />
	        <p style={styles.smallText}>Sélectionne tes 5 artistes préférés.</p>


	  <InputTextBox
		label="Chercher un artiste"
		placeholder="Chercher un artiste"
		value={artistSearch}
		onChange={handleArtistSearchChange}
		readOnly={isReadOnly}
	  />
	        {selectedArtist && (
        <>
          <Spacer height={2} />
          <MusicDisplayerGrid
            type="artist"
            data={[selectedArtist]}
            onSelect={() => {}}
            gridColumns={1}
          />
          <p style={styles.smallText}>
            Êtes-vous sûr de vouloir choisir cet artiste ?{" "}
          </p>
          <div style={styles.confirmationBox}>
            <AppButton
              title="Annuler"
              onClick={handleCancel}
              bgColor="white"
            />
            <Spacer width={4} />
            <AppButton
              title="Confirmer"
              onClick={handleConfirmSelection}
              bgColor={colors.MG_TEAL}
            />
          </div>
        </>
      )}
      {!selectedArtist && (
        <MusicDisplayerGrid
          type="artist"
          data={artistsData}
          onSelect={handleArtistSelect}
        />
      )}
      <h4 style={styles.h4}>Artistes sélectionnés:</h4>
      <MusicDisplayerGrid
        type="artist"
		gridColumns={2}
        data={selectedArtists}
        onSelect={() => {}}
      />
	</div>
  );
};

export default TopGuessPlayerScreen;