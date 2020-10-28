// Base this on the default config at:
// https://github.com/tailwindcss/tailwindcss/blob/master/stubs/defaultConfig.stub.js

const defaultTheme = require('tailwindcss/defaultTheme')

module.exports = {
  future: {
    // removeDeprecatedGapUtilities: true,
    // purgeLayersByDefault: true,
  },
  purge: [],
  theme: {
    extend: {
      colors: {
        linkiro: {
          '900': "#00121f",
          '800': "#00233e",
          '700': "#00355d",
          '600': "#00467c",
          default: "#00589b",
          '400': "#3379af",
          '300': "#669bc3",
          '200': "#99bcd7",
          '100': "#ccdeeb",
          '50': "#e6eef5",
        },
        manila: {
          light: "#fffdfa",
          default: "#fffbf5",
        },
        highlighting: "#ffd760e6",
        dark: "#343a40e6",
        red: {
          '900': "#2a0606",
          '800': "#530b0c",
          '700': "#7d1113",
          '600': "#a61619",
          default: "#d01c1f",
          '400': "#d9494c",
          '300': "#e37779",
          '200': "#eca4a5",
          '100': "#f6d2d2",
          '50': "#fbeeee",
        },
        orange: {
          '900': "#332310",
          '800': "#664520",
          '700': "#99682f",
          '600': "#cc8a3f",
          default: "#ffad4f",
          '400': "#ffbd72",
          '300': "#ffce95",
          '200': "#ffdeb9",
          '100': "#ffefdc",
        },
        green: {
          '900': "#08210e",
          '800': "#10431c",
          '700': "#186429",
          '600': "#208637",
          default: "#28A745",
          '400': "#53b96a",
          '300': "#7eca8f",
          '200': "#a9dcb5",
          '100': "#d4edda",
        },
        blue: {
          '900': "#001c25",
          '800': "#00384a",
          '700': "#00556f",
          '600': "#007194",
          default: "#008db9",
          '400': "#33a4c7",
          '300': "#66bbd5",
          '200': "#99d1e3",
          '100': "#cce8f1",
          '50': "#e6f4f8",
        },
        gray: {
          '100': '#f5f5f5',
          '200': '#eeeeee',
          '300': '#e0e0e0',
          '400': '#bdbdbd',
          default: '#9e9e9e',
          '600': '#757575',
          '700': '#616161',
          '800': '#424242',
          '900': '#212121',
        }
      },
      minHeight: {
        ...defaultTheme.spacing,
      },
    },
    opacity: {
      '0': '0',
      '25': '0.25',
      '50': '0.5',
      '75': '0.75',
      '95': '0.95',
      '100': '1',
    },
  },
  variants: {
    backgroundColor: ['responsive', 'hover', 'focus', 'active'],
    borderColor: ['hover', 'focus', 'active'],
    borderWidth: ['hover', 'focus', 'active'],
  },
  plugins: [
    require('@tailwindcss/typography'),
  ],
}
