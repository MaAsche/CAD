import React from 'react'
import PropTypes from 'prop-types'


const Button = ({text, onClick}) => {

  return (
    <button 
      onClick={onClick}
      className ='btn'>{text}
    </button>
  )
}

Button.propTypes = {
  onClick: PropTypes.func.isRequired
}

export default Button
