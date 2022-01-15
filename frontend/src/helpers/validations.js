let required = (property) => {
  return v => !!v || `${property} é obrigatório`;
}

let email = () => {
  return v => v && /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(.\w{2,3})+$/.test(v) || 'Deve ser um email válido';
}

let minValue = (property, minValue) => {
  return v => (v && parseFloat(v) >= minValue) || `${property} deve ser maior que ${minValue}`;
}

let maxValue = (property, maxValue) => {
  return v => (v && parseFloat(v) <= maxValue) || `${property} deve ser menor que ${maxValue}`;
}

let minLength = (property, minLength) => {
  return v => (v && v.length >= minLength) || `${property} deve ser maior ou igual a ${minLength} caracteres`;
}

let maxLength = (property, maxLength) => {
  return v => (v && v.length <= maxLength) || `${property} deve ser menor ou igual a ${maxLength} caracteres`;
}

export default {
  required,
  email,
  minValue,
  maxValue,
  minLength,
  maxLength
}