import Header from "./components/Header";
import Entries from "./components/Entries";
import AddEntry from "./components/AddEntry";
import { useState, useEffect } from 'react'


function App() {
  const [showAddEntry, setShowAddEntry] = useState(false)
  const [entries, setEntries] = useState([])
  const [isLoaded, setIsLoaded] = useState(false)

  const pictureUrl = '/api/picture'

  const getEntries = () => {
    fetch(`${pictureUrl}/all`)
      .then(rspn => rspn.json())
      .then(json => {
        setEntries(json);
        setIsLoaded(true);
      })
  }

  useEffect(() => {
    getEntries();
    }, []);


  const addEntry = (entry) => {
    setIsLoaded(false);

    let formdata = new FormData();
    formdata.append('file', entry.file)
    formdata.append('description', entry.description)
    console.log(formdata)

    fetch(pictureUrl, {
      method: 'POST',
      body: formdata,
      })
      .then(response => response.json())
      .then(data => {
        console.log('Success:', data);
      })
      .catch((error) => {
        console.error('Error:', error);
      });

    getEntries();
    setShowAddEntry(false);
  }

  const deleteEntry = (id) => {
    setIsLoaded(false);

    fetch(`${pictureUrl}?pictureId=${id}`, { 
      method: 'DELETE',
    })
        .then(() => getEntries() );
  }

  return (
    <div className='container'>
      <Header onAdd={() => setShowAddEntry(!showAddEntry)} showAddEntry={showAddEntry}/>
      {showAddEntry && <AddEntry onAdd={addEntry}/>}
      {isLoaded ? <Entries entries={entries} onDelete={deleteEntry}/> : 'Loading ...'}
    </div>
  );
}

export default App;
