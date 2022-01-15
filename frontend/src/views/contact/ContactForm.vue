<template>
  <v-form class="mt-1" ref="contactform">
    <v-container>
      <v-row>
        <v-col cols="12" md="5" class="py-0 ma-0 my-0">
          <input type="hidden" :value="contact.id" />
          <v-select
            v-model="model.contactType"
            :items="contactTypes"
            :item-text="'description'"
            class="ma-0 pa-0 form-label"
            label="Tipo"
            return-object
            required
            dense
            outlined
            :rules="[required('Tipo')]"
          />
        </v-col>
        <v-col cols="12" md="7" class="py-0 ma-0 my-0">
          <v-text-field
            v-model="model.name"
            @input="model.name = uppercase(model.name)"
            label="Contato"
            class="ma-0 pa-0 form-label"
            dense
            outlined
            required
            counter="80"
            :rules="[
              required('Contato'),
              minLength('Contato', 2),
              maxLength('Contato', 80),
            ]"
            v-mask="getMaskContactType()"
          />
        </v-col>
      </v-row>
      <v-row>
        <v-col cols="12" class="py-0 ma-0 my-0">
          <v-textarea
            v-model="model.description"
            @input="model.description = uppercase(model.description)"
            label="Descrição/Obervação"
            class="ma-0 pa-0 form-label"
            counter="250"
            rows="2"
            clearable
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
              if (!this.$refs.contactform.validate()) return;
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
import { uppercase } from "@/helpers/string-util";
import { LOAD_CONTACT_TYPES } from "@/store/_actiontypes";

export default {
  props: {
    contact: {
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
      uppercase,
    };
  },
  mounted() {
    this.$store.dispatch(`contactTypes/${LOAD_CONTACT_TYPES}`);
  },
  computed: {
    ...mapState({
      contactTypes: (state) => state.contactTypes.contactTypes,
    }),
    model: {
      get() {
        return this.contact;
      },
      set(contact) {
        this.$emit("input", contact);
      },
    },
  },
  methods: {
    reset() {
      this.$refs.contactform.reset();
    },
    getMaskContactType() {
      if (this.model.contactType.name.includes("PHONE"))
        return ["(##) ####-####", "(##) #####-####"];
      return;
    },
  },
};
</script>

<style>
</style>