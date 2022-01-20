<template>
  <v-form class="mt-1" ref="customerform">
    <v-container>
      <v-row>
        <v-col cols="12" md="8" class="py-0 ma-0 my-0">
          <v-text-field
            v-model="model.fullname"
            label="Nome completo"
            class="ma-0 pa-0 form-label"
            dense
            outlined
            required
            counter="80"
            :rules="[
              required('Nome completo'),
              minLength('Nome completo', 2),
              maxLength('Nome completo', 80),
            ]"
          ></v-text-field>
        </v-col>
        <v-col cols="12" md="4" class="py-0 ma-0 my-0">
          <v-text-field
            v-model="model.cpfCnpj"
            label="CPF/CNPJ"
            v-mask="['###.###.###-##', '##.###.###/####-##']"
            class="ma-0 pa-0 form-label"
            dense
            outlined
            required
            :rules="[required('CPF/CNPJ')]"
          />
        </v-col>
      </v-row>
      <v-row>
        <v-col cols="12" md="4" class="py-0 ma-0 my-0">
          <v-text-field
            v-model="model.cep"
            label="CEP"
            class="ma-0 pa-0 form-label"
            v-mask="['#####-###']"
            @keyup="searchCep()"
            dense
            outlined
            required
          />
        </v-col>
        <v-col cols="12" md="8" class="py-0 ma-0 my-0">
          <v-text-field
            v-model="model.address"
            label="Endereço"
            class="ma-0 pa-0 form-label"
            dense
            outlined
            counter="80"
          />
        </v-col>
      </v-row>
      <v-row>
        <v-col cols="12" class="py-0 ma-0 my-0">
          <v-text-field
            v-model="model.addressComplement"
            label="Complemento"
            class="ma-0 pa-0 form-label"
            dense
            outlined
            counter="80"
          />
        </v-col>
      </v-row>
      <v-row>
        <v-col cols="12" md="5" class="py-0 ma-0 my-0">
          <v-text-field
            v-model="model.district"
            label="Bairro"
            class="ma-0 pa-0 form-label"
            dense
            outlined
          />
        </v-col>
        <v-col cols="12" md="4" class="py-0 ma-0 my-0">
          <v-text-field
            v-model="model.city"
            label="Cidade"
            class="ma-0 pa-0 form-label"
            dense
            outlined
          />
        </v-col>
        <v-col cols="12" md="3" class="py-0 ma-0 my-0">
          <v-select
            v-model="model.state"
            :items="states"
            :item-text="'name'"
            return-object
            class="ma-0 pa-0 form-label"
            label="Estado"
            dense
            outlined
          />
        </v-col>
      </v-row>
      <v-row class="justify-end">
        <v-btn
          v-if="showCloseButton"
          outlined
          small
          class="mr-2 red--text font-weight-bold"
          @click="onCloseClick"
          >Cancelar</v-btn
        >
        <v-btn
          outlined
          small
          class="primary--text font-weight-bold"
          :loading="loading"
          @click="
            () => {
              if (!this.$refs.customerform.validate()) return;
              onSubmitClick();
            }
          "
          >Salvar</v-btn
        >
      </v-row>
    </v-container>
  </v-form>
</template>

<script>
import { mapState } from "vuex";
import validations from "@/helpers/validations";
import { LOAD_STATES } from "@/store/_actiontypes";

export default {
  props: {
    customer: {
      type: Object,
    },
    showCloseButton: {
      type: Boolean,
      default: false,
    },
    onSubmitClick: {
      type: Function,
    },
    onCloseClick: {
      type: Function,
    },
    loading: {
      type: Boolean,
    },
  },
  data() {
    return {
      ...validations,
    };
  },
  mounted() {
    this.$store.dispatch(`states/${LOAD_STATES}`);
  },
  computed: {
    ...mapState({
      states: (state) => state.states.states,
    }),
    model: {
      get() {
        return this.customer;
      },
      set(customer) {
        this.$emit("input", customer);
      },
    },
  },
  methods: {
    reset() {
      this.$refs.customerform.reset();
    },
    searchCep() {
      const cep = this.model.cep.replace("-", "");
      if (cep.length == 8) {
        fetch(`https://viacep.com.br/ws/${cep}/json/`).then((response) => {
          response.json().then((data) => {
            this.model.address = data.logradouro.toUpperCase() || null;
            this.model.addressComplement =
              data.complemento.toUpperCase() || null;
            this.model.district = data.bairro.toUpperCase() || null;
            this.model.city = data.localidade.toUpperCase() || null;
            this.model.state = this.states.find(
              (state) => state.name == data.uf
            );
          });
        });
      }
    },
  },
};
</script>

<style>
</style>