import React from 'react'
import { useState } from 'react'

const AddEntry = ({ onAdd }) => {
  const [description, setDescription] = useState('')
  const [file, setFile] = useState(undefined)

  const handleSelectFile = e => {
    if (!e.target.files || e.target.files.length === 0) {
      setFile(undefined)
        return
    }
    setFile(e.target.files[0])
}

  const onSubmit = (e) => {
    e.preventDefault()

    if(!description) {
      alert('Please add a Name')
      return
    }

    if(!file) {
      alert('Please add a File')
      return
    }

    const objectUrl = URL.createObjectURL(file)

    onAdd({description, file})
    setDescription('')
    setFile(null)
  }

  return (
    <form className='add-form' onSubmit={onSubmit}>
      <div className='form-control' >
        <label>Description</label>
        <input 
          type='text' 
          value={description} 
          onChange={(e) => setDescription(e.target.value)} 
          placeholder='Add Name' />
      </div>
      <div className='form-control' >
        <label>Picture</label>
        <input 
          type='file' 
          onChange={(e) => handleSelectFile(e)}/>
      </div>
      <input className='btn btn-block' type='submit' value='Save' />
    </form>
  )
}

export default AddEntry
